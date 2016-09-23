package com.activiti.deployment;

import com.activiti.util.ExceptionUtil;
import junit.framework.Assert;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Xg
 * @Date 2016-09-23 10:07
 * @Desc 部署测试问价
 */
public class DemolymentTest {
    private static Logger log = LoggerFactory.getLogger(DemolymentTest.class);
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     *  流程动态部署
     */
    @Test
    public void testDynamicDeploy(){
        // 1. 从头创建模型
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("my-process");
        // 用户任务
        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1","First task","Fred"));
        process.addFlowElement(createUserTask("task2","Second task","John"));
        process.addFlowElement(createEndEvent());
        // 流动序列
        process.addFlowElement(createSequenceFlow("start","task1"));
        process.addFlowElement(createSequenceFlow("task1","task2"));
        process.addFlowElement(createSequenceFlow("task2","end"));

        // 2. 生成图像信息
        new BpmnAutoLayout(model).execute();

        // 3. 将进程部署到引擎
        Deployment deployment = activitiRule.getRepositoryService().createDeployment()
                .addBpmnModel("dynamic-model.bpmn",model).name("Dynamic process deployment").deploy();

        // 4. 启动一个进程实例
        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("my-process");

        // 5. 检查是否有可用任务
        List<Task> tasks = activitiRule.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).list();

        Assert.assertEquals(1,tasks.size());
        Assert.assertEquals("First task",tasks.get(0).getName());
        Assert.assertEquals("Fred",tasks.get(0).getAssignee());

        try {
            // 6. 将进程图保存到文件中
            InputStream processDiagram = activitiRule.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
            FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));

            // 7.将BPMN XML保存到文件
            InputStream processBpmn = activitiRule.getRepositoryService().getResourceAsStream(deployment.getId(),"dynamic-model.bpmn");
            FileUtils.copyInputStreamToFile(processBpmn,new File("target/process_bpmn.xml"));

        }catch (IOException e){
            log.info("这里发生了IO异常："+ ExceptionUtil.getErrorInfo(e));
        }
    }

    /**
     *  启动节点
     */
    protected StartEvent createStartEvent(){
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }
    /**
     *  用户任务
     */
    protected UserTask createUserTask(String id,String name,String assignee){
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        userTask.setAssignee(assignee);
        return userTask;
    }
    /**
     *  结束节点
     */
    protected EndEvent createEndEvent(){
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
    /**
     *  流动序列
     */
    protected SequenceFlow createSequenceFlow(String from, String to){
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setSourceRef(from);
        sequenceFlow.setTargetRef(to);
        return sequenceFlow;
    }
}
