package com.bzgwl.mybatis_plus;

import com.bzgwl.mybatis_plus.web.activitiListeners.MyExecutionListener;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("all")
public class ActivityTest {


    @Value("${spring.datasource.url}")
    private  String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    @Value("${spring.datasource.driverClassName}")
    private  String driverClassName;

    @Autowired(required = false)
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void test1(){


    }

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
                .name("请假流程4")
                .addClasspathResource("process/qingjia_process.bpmn")
//                .addClasspathResource("processes/myProcess.png")
                .deploy();
        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名称："+deployment.getName());
    }

    //Inputstream方式
    @Test
    public void deployByInputStream() throws FileNotFoundException {
        //获取资源相对路径
        String bpmnPath = "D:/process/end-el3.bpmn";
//        String bpmnPath = "D:/process/diagram7.bpmn";
//        String bpmnPath = "D:/process/diagram22.bpmn";

        //读取资源作为一个输入流
        FileInputStream bpmnfileInputStream = new FileInputStream(bpmnPath);

        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("请假流程-EL")
                .addInputStream("请假复杂流程-EL.bpmn",bpmnfileInputStream)
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署Name："+deployment.getName());
        System.out.println("部署时间："+deployment.getDeploymentTime().toLocaleString());
    }


    /**启动流程实例分配任务给个人*/
    @Test
    public void start() {

        MyExecutionListener myExecutionListener = new MyExecutionListener();

        String processDefinitionKey ="Process_12-2";//每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的
        HashMap<String, Object> variables=new HashMap<>();
//        variables.put("userKey", userKey);//userKey在上文的流程变量中指定了
        variables.put("day", 20);
        variables.put("user", "tony");
        variables.put("msgs","创业失败，回家继承10亿资产去！！！");
        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey,variables);

        System.out.println("流程定义ID:"+instance.getProcessDefinitionId()); //myProcess_1:2:3e0c77bf-0a74-11ea-af9a-005056c00001
        System.out.println("流程实例ID:"+instance.getId());   // 6d2fe209-0a74-11ea-9ca0-005056c00001
    }

    /**查询当前人的个人任务*/
    @Test
    public void findTask(){
        String assignee = "tony";
        List<Task> list = taskService.createTaskQuery()//创建任务查询对象
                .taskAssignee(assignee)//指定个人任务查询
                .list();
        if(list!=null && list.size()>0){
            for(Task task:list){
                System.out.println("任务ID:"+task.getId()); //6d34eb23-0a74-11ea-9ca0-005056c00001
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("getOwner:"+task.getOwner());
                System.out.println("getCategory:"+task.getCategory());
                System.out.println("getDescription:"+task.getDescription());
                System.out.println("getFormKey:"+task.getFormKey());
                Map<String, Object> map = task.getProcessVariables();
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }
                for (Map.Entry<String, Object> m : task.getTaskLocalVariables().entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }

            }
        }
    }

    @Test
    public void setAssigne(){
    }


    @Test
    public void completeTask(){
        //流程实例 id  = 任务ID
        taskService.complete("ff8605c6-172a-11ea-a843-6214b3baacc3");
    }

    @Test
    public void taskHis(){
//        taskService.complete("6d34eb23-0a74-11ea-9ca0-005056c00001");
        HistoricTaskInstance his = historyService.createHistoricTaskInstanceQuery().taskAssignee("tony").singleResult();
        System.out.println(his.getAssignee());
    }

    @Test
    public void delete(){
        String deployId = "cecd26cd-14a2-11ea-bde6-6214b3baacc3";
        //ture  级联删除 ，会将 字节表  流程定义表  相关联的历史表  全部删除
        repositoryService.deleteDeployment(deployId,true);
    }

    @Test
    public void deleteAll(){
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        list.forEach(e ->{
            repositoryService.deleteDeployment(e.getId(),true);
        });
    }

    @Test
    public void test2(){

        String processDefinitionId ="Process_12-2:1:a1bf5fdb-1ca0-11ea-972b-6214b3baacc3";
        //获取bpmnModel对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //因为我们这里只定义了一个Process 所以获取集合中的第一个即可
        Process process = bpmnModel.getProcesses().get(0);
        //获取所有的FlowElement信息
        Collection<FlowElement> flowElements = process.getFlowElements();
        Map<String,String> map = new HashMap<>();
        for (FlowElement flowElement : flowElements) {
            //如果是任务节点
//            if (flowElement instanceof UserTask) {
//                UserTask userTask = (UserTask) flowElement;
//                //获取入线信息
//                List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
//                for (SequenceFlow sequenceFlow : incomingFlows) {
//                    System.out.println(sequenceFlow.getId() + "-" + sequenceFlow.getConditionExpression() + "--" + sequenceFlow.getDocumentation() + "-"
//                            + sequenceFlow.getSourceRef() + "--" + sequenceFlow.getTargetRef() + "-");
//                }
//
//            }

            if (flowElement instanceof SequenceFlow) {
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;

                String ref = sequenceFlow.getSourceRef();
                String targetRef = sequenceFlow.getTargetRef();
                map.put(ref+targetRef,sequenceFlow.getId()+"===="+sequenceFlow.getName());
            }
        }

        Collection<String> values = map.values();
        values.forEach(s-> System.out.println(s));

        String instanceId ="c00e50ec-1ca0-11ea-972b-6214b3baacc3";
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .list();
        list.forEach(s-> System.out.println(s.getActivityId() +"===="+s.getActivityName()));

    }

}
