package com.activiti.controller;

import com.activiti.util.ExceptionUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     *   默认跳转页面
     *
     * @param
     * @Author Xg
     * @Date 2016/9/28 16:27
     */
    @RequestMapping("/")
    public String defaultView(){
        return "index";
    }
    /**
     *  跳转到index页面
     *
     * @param
     * @Author Xg
     * @Date 2016/9/27 14:43
     */
    @RequestMapping("/index.do")
    public String indexView(HttpServletRequest request){
        return "index";
    }
    /**
     *  登录页面
     *
     * @param
     * @Author Xg
     * @Date 2016/9/28 16:16
     */
    @RequestMapping("login.do")
    public String login(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }
    /**
     *  登录验证
     *
     * @param   userName 用户名
     * @param   passWord 密码
     * @Author Xg
     * @Date 2016/9/28 16:13
     */
    @RequestMapping("checkLogin.do")
    public String checkLogin(HttpServletRequest request,
                             @RequestParam("userName")String userName,
                             @RequestParam("passWord")String passWord){
        log.debug("logon request: {username={}, password={}}", userName, passWord);
        boolean checkPassword = identityService.checkPassword(userName, passWord);

        HttpSession session = request.getSession(true);

        if(checkPassword)
        {
            User user = identityService.createUserQuery().userId(userName).singleResult();
            session.setAttribute("user",user);
            GroupQuery groupQuery =  identityService.createGroupQuery();
            List<Group> groupList =  groupQuery.groupMember(userName).list();

            session.setAttribute("groups",groupList);

            String[] groupNames = new String[groupList.size()];

            for(int i=0;i<groupNames.length;i++){
                groupNames[i] = groupList.get(i).getName();
            }

            session.setAttribute("groupNames", ArrayUtils.toString(groupNames));
            return "redirect:main.do";
        }
        else {
            return "redirect:login.do";
        }
    }
    /**
     *  退出登录
     *
     * @param
     * @Author Xg
     * @Date 2016/9/28 16:33
     */
    @RequestMapping("outlogin.do")
    public String outLoign(HttpServletRequest request){
        request.getSession().invalidate();
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
    public String mainView(HttpServletRequest request,
            Model model){
        User user =request.getSession(true).getAttribute("user")==null?null:(User)request.getSession(true).getAttribute("user");

        List<Group> groups = request.getSession(true).getAttribute("groups")==null?null:(List<Group>)request.getSession(true).getAttribute("groups");


        if(null==user){
            return "redirect:login.do";
        }
        else {
            model.addAttribute("user",user);
            model.addAttribute("groups",groups);
            /**
             * 所有部署的任务
             */
            List<ProcessDefinition> pdList =repositoryService.createProcessDefinitionQuery().list();

            model.addAttribute("pdList",pdList);
            /**
             * 该用户所有可以认领的任务
             */
            List<Task> groupTasks = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();

            List<Task> userTasks = taskService.createTaskQuery().taskAssignee(user.getId()).list();
            model.addAttribute("userTasks",userTasks);
            model.addAttribute("groupTasks",groupTasks);
            /**
             * 查看任务实例
             */
            List<Task> taskList = taskService.createTaskQuery().list();
            model.addAttribute("taskList",taskList);
            /**
             * 历史流程
             */
            List<HistoricProcessInstance> hpiList = historyService.createHistoricProcessInstanceQuery().finished().list();
            model.addAttribute("hpiList",hpiList);
        }
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
