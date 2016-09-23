package com.activiti.bpmn;

import com.activiti.util.ExceptionUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Xg
 * @Date 2016-09-23 14:11
 * @Desc  基础测试
 */
public class BpmnTest {

    private static Logger log = LoggerFactory.getLogger(BpmnTest.class);    // 日志

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine(); //流程引擎

    /**
     *  部署流程定义
     */
    @Test
    public void bpmnTest(){
        Deployment deployment = processEngine.getRepositoryService()    // 与流程定义和部署对象相关的Service
                .createDeployment()     // 与流程定义和部署对象相关的Service
                .name("入门程序")   //添加部署的名称
                .addClasspathResource("bpmn/BpmnTest.bpmn")     //从resources的资源中加载，一次只能加载一个文件
                .deploy();  //完成部署
        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名字："+deployment.getName());
    }
    /**
     *  启动流程实例
     *  @Param processDefinitionKey 流程定义的key
     */
    @Test
    public void startProcess(){
        // 流程定义的key
        String processDefinitionKey = "myProcess_1";
        ProcessInstance pi = processEngine.getRuntimeService()  //与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey); //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID："+pi.getId());
        System.out.println("流程定义ID："+pi.getProcessDefinitionId());
    }
    /**
     *  查询当前人的个人任务
     *  @Parn assgine 人名
     */
    @Test
    public void findMyPersonalTask(){
        String assgine = "task1";
        List<Task> list = processEngine.getTaskService()    //与正在执行的任务管理相关的Service
                .createTaskQuery()  //创建任务查询对象
                .taskAssignee(assgine)  //指定个人任务查询，指定办理人
                .list();
        if(list != null && list.size() > 0){
            for(Task task : list){
                System.out.println("任务ID："+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }else{
            System.out.println("---------- 空 ----------");
        }
    }
    /**
     *  完成我的任务
     *  @Param taskID 任务ID
     */
    @Test
    public void compeleteMyPersonalTask(){
        // 任务ID
        String taskID = "5002";
        processEngine.getTaskService()  //与正在执行的任务管理相关的Service
                .complete(taskID);
        System.out.println("完成任务：任务ID："+taskID);
    }
    /**
     *  查看流程图
     *  @Parm deploymentId 流程部署ID
     */
    @Test
    public void viewPic(){
        String deploymentId = "1";
        // 获取图片资源名称
        List<String > list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        // 定义图片名称
        String resourceName = "";
        if(list != null && list.size()>0){
            for(String name : list){
                resourceName = name;
            }
        }else{
            System.out.println("----------  空  -----------");
        }
        try {
            // 获取图片的输入流
            InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId,resourceName);
            // 将生成的图片保存
            File file = new File("D:/"+resourceName);
            // 将输入流写入
            FileUtils.copyInputStreamToFile(in,file);
        }catch (IOException e){
            log.info("写入文件异常："+ ExceptionUtil.getErrorInfo(e));
        }

    }
    /**
     *  查看流程状态
     *  @Parm processInstanceId 流程实例ID
     */
    @Test
    public void idProcessEnd(){
        String processInstanceId = "10001";
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if(pi == null){
            System.out.println("流程已经结束");
        }else{
            System.out.println("当前流程在："+pi.getActivityId());
        }
    }
    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件,where条件*/
//                .deploymentId(deploymentId)//使用部署对象ID查询
//                .processDefinitionId(processDefinitionId)//使用流程定义ID查询
//                .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//                .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//                .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//                .singleResult();//返回惟一结果集
//                .count();//返回结果集数量
//                .listPage(firstResult, maxResults);//分页查询
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                System.out.println("流程定义的Id："+pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称："+pd.getName());//对应bpmn文件中的name属性值
                System.out.println("流程定义的key:"+pd.getKey());//对应bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID："+pd.getDeploymentId());
                System.out.println("#########################################################");
            }
        }else{
            System.out.println("------- 空 --------");
        }
    }
    /**
     *  删除流程定义（流程部署ID）
     *  @Parm deploymentId
     */
    @Test
    public void deleteProcessDefinition(){
        //使用部署ID，完成删除
        String deploymentId = "1";
        /**
         * 不带级联的删除
         *    只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//        processEngine.getRepositoryService()
//                .deleteDeployment(deploymentId);

        /**
         * 级联删除
         *    不管流程是否启动，都能可以删除
         */
        processEngine.getRepositoryService()//
                .deleteDeployment(deploymentId, true);
        System.out.println("删除成功！");
    }
    /**
     *  删除流程定义（流程定义名称）
     *  @Parm processDefinitionKey 流程定义的名称
    */
    @Test
    public void deleteProcessDefinitionByKey(){
        //流程定义的key
        String processDefinitionKey = "myProcess_1";
        //先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()//
                .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
                .list();
        //遍历，获取每个流程定义的部署ID
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                //获取部署ID
                String deploymentId = pd.getDeploymentId();
                processEngine.getRepositoryService()
                        .deleteDeployment(deploymentId, true);
            }
        }
    }
    /**
     *  查询历史流程实例（流程定义名称）
     *  @Parma processDefinitionKey 流程定义的名称
     */
    @Test
    public void findHistoryProcessInstance(){
        String processDefinitionKey = "myProcess_1";
        List<HistoricProcessInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processDefinitionKey(processDefinitionKey)
                .list();
        if(list != null && list.size() > 0){
            for(HistoricProcessInstance hpi : list){
                System.out.println(hpi.getId()+"    "+hpi.getProcessDefinitionId()+"    "+hpi.getStartTime()+"    "+hpi.getEndTime()+"     "+hpi.getDurationInMillis());
            }
        }else{
            System.out.println("-------- 空 --------");
        }
    }
}
