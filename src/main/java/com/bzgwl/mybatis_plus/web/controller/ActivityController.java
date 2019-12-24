package com.bzgwl.mybatis_plus.web.controller;

import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import com.bzgwl.mybatis_plus.utils.StringUtil;
import com.bzgwl.mybatis_plus.web.activitiListeners.MyExecutionListener;
import com.bzgwl.mybatis_plus.web.entity.QingjForm;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Professor_Kong
 * @Description
 * @Date 2019/11/20
 */
@Controller
@RequestMapping("web/act")
@SuppressWarnings("all")
public class ActivityController {


    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private MyExecutionListener myExecutionListener;

    /**
     * 新建工作 -页面跳转
     * @return
     */

    @RequestMapping("/index")
    public String index(){
        return "act/act";
    }

    @RequestMapping("/myApply")
    public String myApply(){
        return "act/act-my-apply";
    }

    @RequestMapping("/waiting")
    public String waiting(){
        return "act/act-my-waiting";
    }


    /**
     * 工作流  流程图 查看
     * @return
     */
    @RequestMapping("/view")
    public String view(){
        return "act/act-view";
    }

    /**
     * 工作流  流程图  个人流程查看
     * @return
     */
    @RequestMapping("/apply-my-view")
    public ModelAndView applyMyView(ModelAndView modelAndView,String instanceId){

        modelAndView.setViewName("act/act-my-view");
        modelAndView.addObject("instanceId",instanceId);
        return modelAndView;
    }

    /**
     * @param resp
     * @return
     */
    @RequestMapping("/addIndex")
    public String addIndex(HttpServletResponse resp){

        return "act/act-add";
    }


    /**
     * 流程模板列表
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/proDefList")
    @ResponseBody
    public JsonResponse modelList(Integer page , Integer limit){
        JsonResponse jsonResponse = new JsonResponse();

        long count = repositoryService.createProcessDefinitionQuery().count();
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .listPage(page-1,limit);

        List<Map<String,String>> list =processDefinitionList.stream().map( (s)-> {
            Map<String,String> map = new HashMap();
            map.put("name", s.getName());
            map.put("des", s.getDescription());
            map.put("version", String.valueOf(s.getVersion()));
            map.put("key",s.getKey());
            map.put("id",s.getId());
            return map;
        }).collect(Collectors.toList());

        jsonResponse.setCount(count);
        jsonResponse.setData(list);
        return jsonResponse;
    }

    /**
     * 我的待办
     * @return
     */
    @RequestMapping("/myWaitingToDo")
    @ResponseBody
    public JsonResponse getActList(Integer page , Integer limit){
        JsonResponse jsonResponse = new JsonResponse();

        User user =  (User)SecurityUtils.getSubject().getPrincipal();

        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee(user.getUsername())
                .listPage(page-1,limit);
        long count = taskService.createTaskQuery()
                .taskAssignee(user.getUsername()).count();

        List<Map<String,String>> list = new ArrayList<>();


        String username ="";
        if(taskList!=null && taskList.size()!=0){
            HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                    .variableName("username").taskId(taskList.get(0).getId()).singleResult();

            username = (String)historicVariableInstance.getValue();
        }

        for(Task t : taskList){
            Map map2 = new HashMap();
            t.setDescription(username+"-请假申请单-"+t.getCreateTime());
            map2.put("description",username+"-请假申请单-"+t.getCreateTime());
            map2.put("name",t.getName());
            map2.put("executionId",t.getExecutionId());
            map2.put("status",t.getDelegationState());
            map2.put("id",t.getId());
            list.add(map2);

        }
        jsonResponse.setData(list);
        jsonResponse.setCount(count);
        return jsonResponse;
    }


    /**
     * 流程部署-- 导入方式
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/importProcess")
    @ResponseBody
    public JsonResponse importProcess(@RequestParam("file") MultipartFile file) throws Exception{
        JsonResponse jsonResponse = new JsonResponse();
        Resource resource = file.getResource();
        InputStream inputStream = resource.getInputStream();
        try {
            Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                    .createDeployment()//创建部署对象
                    .name("请假流程-EL")
                    .addInputStream("请假复杂流程-EL.bpmn", file.getInputStream())
                    .deploy();//完成部署
            System.out.println("部署ID：" + deployment.getId());//1
            System.out.println("部署Name：" + deployment.getName());
            System.out.println("部署时间：" + deployment.getDeploymentTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResponse;
    }

    /**
     * 一键删除
     * @return
     */
    @RequestMapping("/oneDel")
    @ResponseBody
    public JsonResponse oneDel(){
        JsonResponse jsonResponse = new JsonResponse();
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        list.forEach(e ->{
            repositoryService.deleteDeployment(e.getId(),true);
        });
        return jsonResponse;
    }

    /**
     * 一键删除
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public JsonResponse batchDel( @RequestParam(value = "ids[]")String[] ids){
        JsonResponse jsonResponse = new JsonResponse();

        jsonResponse.setMsg("未选择有效数据！");
        if(ids == null){
            return jsonResponse;
        }
        List<String> list = Arrays.asList(ids);
        list.forEach(e ->{
            repositoryService.deleteDeployment(e,true);
        });
        return jsonResponse;
    }


    @RequestMapping("/newProBefore")
    public String newProBefore(){
        return "act/act-add-form";
    }

    @RequestMapping("/addNewApply")
    @ResponseBody
    public JsonResponse addNewApply(QingjForm qingjForm){
        JsonResponse jsonResponse = new JsonResponse();


        if(StringUtil.isEmpty(qingjForm.getKey())){
            jsonResponse.setCode("1");
            jsonResponse.setMsg("请选择新建的模板！！");
            return jsonResponse;
        }
        HashMap<String, Object> variables=new HashMap<>();
        if(StringUtil.isNotEmpty(qingjForm.getUsername())){
            variables.put("username", qingjForm.getUsername());
        }
        if(StringUtil.isNotEmpty(qingjForm.getTypes())){
            variables.put("types",qingjForm.getTypes());
        }
        if(StringUtil.isNotEmpty(qingjForm.getContents())){
            variables.put("contents",qingjForm.getContents());
        }
        Integer days = qingjForm.getDays();
        if(days!=null) {
            if (days <= 3){
                variables.put("user", "xiaohua");
            }
            if (days >3 && days <= 7) {
                variables.put("user", "wangls");
            }
            if (days >7 && days <= 15) {
                variables.put("user", "lizr");
            }

            variables.put("days", days);
        }

        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();

        //设置发起人
        Authentication.setAuthenticatedUserId(principal.getUsername());

        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey(qingjForm.getKey(),variables);

        System.out.println("流程定义ID:"+instance.getProcessDefinitionId());
        System.out.println("流程实例ID:"+instance.getId());
        return jsonResponse;
    }

    /**
     * 我的申请
     * @param page
     * @param limit
     * @return
     */

    @RequestMapping("/myApplyList")
    @ResponseBody
    public JsonResponse myApplyList(Integer page , Integer limit){
        JsonResponse jsonResponse = new JsonResponse();
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();

        //创建查询对象,查询该用户发起的流程
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .startedBy(principal.getUsername())
                .listPage(page - 1, limit);

        long count = historyService.createHistoricProcessInstanceQuery()
                .startedBy(principal.getUsername())
                .count();

        List<Map<String,Object>> mapList = new ArrayList<>();
        list.forEach(s->{
            Map<String,Object> map = new HashMap();
            map.put("name",s.getStartUserId()+"_"+s.getProcessDefinitionName());
            map.put("startTime",s.getStartTime());
            map.put("id",s.getId());

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(s.getId())
                    .singleResult();
            if(processInstance==null){
                map.put("status","已完成");
            }else{
                map.put("status","执行中");
            }

            mapList.add(map);
        });
        jsonResponse.setData(mapList);
        jsonResponse.setCount(count);
        return jsonResponse;
    }

    @RequestMapping("/approval_msg")
    @ResponseBody
    public JsonResponse approvalPass(String id,String msg){
        JsonResponse jsonResponse = new JsonResponse();

        if(StringUtil.isNotEmpty(msg)){
            String str= msg.replace("\"", "");
            taskService.setVariable(id,"msg",str);
        }
        taskService.complete(id);
        return jsonResponse;
    }

    @RequestMapping("/getApply_status")
    @ResponseBody
    public Map<String,Object> getApplyStatus(String instanceId){

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();
        //获取bpmnModel对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        //因为我们这里只定义了一个Process 所以获取集合中的第一个即可
        Process process = bpmnModel.getProcesses().get(0);
        //获取所有的FlowElement信息
        Collection<FlowElement> flowElements = process.getFlowElements();

        Map<String,String> map = new HashMap<>();
        for (FlowElement flowElement : flowElements) {
            //判断是否是连线
            if (flowElement instanceof SequenceFlow) {
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                String ref = sequenceFlow.getSourceRef();
                String targetRef = sequenceFlow.getTargetRef();
                map.put(ref+targetRef,sequenceFlow.getId());
            }
        }

        //获取流程实例 历史节点(全部)
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .list();
        //各个历史节点   两两组合 key
        Set<String> keyList = new HashSet<>();
        for (HistoricActivityInstance i: list) {
            for(HistoricActivityInstance j : list){
                if(i!=j){
                    keyList.add(i.getActivityId()+j.getActivityId());
                }
            }
        }
        //高亮连线ID
        Set<String> highLine = new HashSet<>();
        keyList.forEach(s->highLine.add(map.get(s)));


        //获取流程实例 历史节点（已完成）
        List<HistoricActivityInstance> listFinished = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .finished()
                .list();
        //高亮节点ID
        Set<String> highPoint = new HashSet<>();
        listFinished.forEach(s->highPoint.add(s.getActivityId()));

        //获取流程实例 历史节点（待办节点）
        List<HistoricActivityInstance> listUnFinished = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .unfinished()
                .list();

        //需要移除的高亮连线
        Set<String> set = new HashSet<>();
        //待办高亮节点
        Set<String> waitingToDo = new HashSet<>();
        listUnFinished.forEach(s->{
            waitingToDo.add(s.getActivityId());

            for (FlowElement flowElement : flowElements) {
                //判断是否是 用户节点
                if (flowElement instanceof UserTask) {
                    UserTask userTask = (UserTask) flowElement;

                    if(userTask.getId().equals(s.getActivityId())){
                        List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
                        //因为 高亮连线查询的是所有节点  两两组合 把待办 之后  往外发出的连线 也包含进去了  所以要把高亮待办节点 之后 即出的连线去掉
                        if(outgoingFlows!=null&& outgoingFlows.size()>0){
                            outgoingFlows.forEach(a-> {
                                if(a.getSourceRef().equals(s.getActivityId())){
                                    set.add(a.getId());
                                }
                            });
                        }
                    }
                }
            }
        });

        highLine.removeAll(set);


        //获取当前用户
        User sysUser = getSysUser();
        Set<String> iDo = new HashSet<>(); //存放 高亮 我的办理节点
        //当前用户已完成的任务
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(sysUser.getUsername())
                .finished()
                .processInstanceId(instanceId).list();

        taskInstanceList.forEach(a->iDo.add(a.getTaskDefinitionKey()));

        Map<String,Object> reMap = new HashMap<>();
        reMap.put("highPoint",highPoint);
        reMap.put("highLine",highLine);
        reMap.put("waitingToDo",waitingToDo);
        reMap.put("iDo",iDo);

        return reMap;
    }

    @RequestMapping("/hisWork")
    @ResponseBody
    public JsonResponse hisWork(Integer page , Integer limit){
        JsonResponse jsonResponse = new JsonResponse();
        User sysUser = getSysUser();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(sysUser.getUsername())
                .listPage(page - 1, limit);

        long count = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(sysUser.getUsername())
                .count();

        List<Map<String,Object>> mapList = new ArrayList<>();
        historicTaskInstances.forEach(s->{

            HistoricProcessInstance his = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(s.getProcessInstanceId()).singleResult();

            Map<String,Object> map = new HashMap();
            map.put("name",his.getStartUserId()+"_"+his.getProcessDefinitionName());
            map.put("startTime",his.getStartTime());
            map.put("id",his.getId());

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(his.getId())
                    .singleResult();
            if(processInstance==null){
                map.put("status","已完成");
            }else{
                map.put("status","执行中");
            }
            mapList.add(map);
        });
        jsonResponse.setData(mapList);
        jsonResponse.setCount(count);

        return jsonResponse;
    }

    @RequestMapping("/hisWorkIndex")
    public String goToHisWorkIndex(){
        return "act/act-his-work";
    }

    public static User getSysUser(){
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        return principal;
    }

}
