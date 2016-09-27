package com.activiti.controller;

import com.activiti.util.ExceptionUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Xg
 * @Date 2016-09-27 11:46
 * @Desc
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    /**
     *  跳转到index页面
     *
     * @param
     * @Author Xg
     * @Date 2016/9/27 14:43
     */
    @RequestMapping("/")
    public String indexView(){
        return "index";
    }
    /**
     *   跳转到main页面
     *
     * @param
     * @Author Xg
     * @Date 2016/9/27 15:23
     */
    @RequestMapping("main.do")
    public String mainView(Model model){
        /**
         * 所有部署的任务
         */
        List<ProcessDefinition> pdList =repositoryService.createProcessDefinitionQuery().list();

        model.addAttribute("pdList",pdList);

        return "main";
    }
    /**
     *   启动流程定义
     *
     * @param processDefId 流程定义ID
     * @Author Xg
     * @Date 2016/9/27 16:32
     */
    @RequestMapping("startProcessDefinition.do")
    public String startProcessDefinition(@RequestParam("processDefId") String processDefId){
        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId("YiviS");
        runtimeService.startProcessInstanceById(processDefId);
        return "redirect:main.do";
    }
    /**
     *  查看流程定义
     *
     * @param processDefId 流程定义ID
     * @Author Xg
     * @Date 2016/9/27 17:28
     */
    @RequestMapping("viewprocessDef")
    public String viewprocessDef(HttpServletResponse response,
            @RequestParam("processDefId") String processDefId){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getResourceName());
        try {
            response.getOutputStream().write(IoUtil.readInputStream(inputStream,"processDefInputStream"));
        }catch (IOException e){
            log.info("文件IO异常："+ ExceptionUtil.getErrorInfo(e));
        }
        return null;
    }
    /**
     *  查看流程定义图片
     *
     * @param processDefId 流程定义ID
     * @Author Xg
     * @Date 2016/9/27 17:37
     */
    @RequestMapping(value = "viewprocessDefImage.do")
    public String viewprocessDefImage(HttpServletResponse response,
                                      @RequestParam("processDefId") String processDefId){
        //根据流程定义id查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();

        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());

        try {
            response.getOutputStream().write(IoUtil.readInputStream(inputStream,"processDefInputStream"));
        }catch (IOException e){
            log.info("文件IO异常："+ ExceptionUtil.getErrorInfo(e));
        }
        return null;
    }
    /**
     *  删除流程定义
     *
     * @param processDefId 流程定义ID
     * @Author Xg
     * @Date 2016/9/27 17:42
     */
    @RequestMapping(value = "remove.do")
    public String remove(@RequestParam("processDefId") String processDefId){

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();

        repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        return "redirect:main.do";
    }
}
