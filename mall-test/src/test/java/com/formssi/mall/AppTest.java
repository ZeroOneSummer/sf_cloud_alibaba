package com.formssi.mall;

import static org.junit.Assert.assertTrue;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private String procIntanceId;
    private static ProcessEngine processEngine = null;

    static {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 这一步操作简单来说就是部署流程，例如一些流程名字或者类别啥的
     * 可以在这一步进行部署，这些信息会加载到相应的流程定义表里。
     */
    @Test
    public void deploy() {
        //取的流程引擎对象

        //获取仓库服务:管理流程定义
        RepositoryService repositoryService=processEngine.getRepositoryService();
        //创建一个部署的构建器
        Deployment deploy=repositoryService.createDeployment()
                .addClasspathResource("activiti/activitiEmployeeProcess.bpmn")//在类路径中添加资源，一次只能添加一个资源
                .addClasspathResource("activiti/activitiEmployeeProcess.png")//两个资源加载完毕
                .name("请假单流程")//设置部署名称
                .category("办公类别")//设置部署的类别
                .deploy();


        System.out.println("部署的id"+deploy.getId());
        System.out.println("部署的名字"+deploy.getName());
    }

    /**
     * 这一步主要是开始执行任务了，这里模拟了一下任务的执行操作
     * 各个审批人之间的联系
     */
    @Test
    public void startProcess() {
        //获取流程定义表中的key  项目中可以进行实施查询
        String processKey="myProcess";
        //取运行时的服务
        RuntimeService runtimeService=processEngine.getRuntimeService();
        ProcessInstance pi= runtimeService.startProcessInstanceByKey(processKey);//通过流程定义的key执行流程
        System.out.println("流程实例的id="+pi.getId());
        System.out.println("流程定义的id="+pi.getProcessDefinitionId());
        //此时第一流程已经开启，可以在执行对象表查询到信息
    }

    /**
     * 这个方法主要是查询流程用的方法，利用此方法查询出流程执行到哪一步了。当前执行人的名字和id等一些信息
     */
    @Test
    public void queryTask() {
        //任务办理人  由于是刚开始执行任务所以任务的办理人是张三
        String name ="张三";
        TaskService taskService=processEngine.getTaskService();
        TaskQuery taskQuery=taskService.createTaskQuery();//创建了一个查询对象
        List<Task> taList=taskQuery.taskAssignee(name).list();//办理人的任务列表

        //遍历任务列表看一看
        for(Task task: taList) {
            System.out.println("任务办理人="+task.getAssignee());
            System.out.println("任务id="+task.getId());
            System.out.println("任务的名字="+task.getName());
        }
    }

    /**
     * 这个操作会让流程结束，在实际应用中到最后一级审批人就可以让流程执行完成操作
     */
    @Test
    public void compileTask() {
        String taskId="204";
        //接的是一个任务的id
        processEngine.getTaskService().complete(taskId);//参数是一个任务的id
        System.out.println("任务执行完毕");
    }


    //卸载部署流程文件
    @Test
    public void testDeleteBpmnFile(){
        //创建工作流引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程文件(卸载部署文件)
        repositoryService.deleteDeployment("2501");
        System.out.println("删除部署文件 id=2501");
    }


    //启动流程实例
    @Test
    public void StartInstance(){
        //获取工作流引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println("启动当前实例的id值="+processInstance.getId()); //对应的是act_ru_task 这个表
        procIntanceId = String.valueOf(processInstance.getId());
        System.out.println(procIntanceId);
    }

    //获取当前任务节点对象及完成此任务节点的是谁
    @Test
    public void testGetAndCompleteFirstUserTask(){
        //创建工作流引擎
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        //TaskService：管理Task的节点
        TaskService taskService=processEngine.getTaskService();
        //获取当前流程实例id=7501的，当前任务节点对象
        Task task=taskService.createTaskQuery().processInstanceId(procIntanceId).singleResult();
        System.out.println("第一个任务名="+task.getName());
        System.out.println("第一个任务Id="+task.getId());
        //完成此任务节点
        taskService.complete(task.getId());
    }



    //判断此实例流程是否结束
    @Test
    public void testFlowEnd() {
        //获取工作流引擎对象
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        TaskService taskService=processEngine.getTaskService();

        //获取实例id=5001，当前任务节点对象
        Task task=taskService.createTaskQuery().processInstanceId(procIntanceId).singleResult();
        if(task==null) {
            System.out.println("此实例流程已经结束");
        }else
            System.out.println("此实例流程并没有结束处理");
    }

    /*
     * 加载参数实例（参数从一个节点传输到另一个节点）
     * */
    @Test
    public void testSetTaskVariable() {
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        TaskService taskService=processEngine.getTaskService();
        //根据当前的实例id，获取当前的任务节点
        Task task=taskService.createTaskQuery().processInstanceId(procIntanceId).singleResult();
        //设置当前任务的变量值
        taskService.setVariable(task.getId(),"username","胡图图");;
        taskService.setVariable(task.getId(),"age",22);
    }


    /*查询上一阶段传下来的值*/
    @Test
    public void testGetTaskVariable() {
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        TaskService taskService=processEngine.getTaskService();
        //根据当前的实例id，获取当前的任务节点
        Task task=taskService.createTaskQuery().processInstanceId(procIntanceId).singleResult();
        System.out.println(taskService.getVariable(task.getId(), "username"));
        System.out.println(taskService.getVariable(task.getId(), "age"));
    }

    /**
     * 全部流程实例的挂起和激活
     */
    @Test
    public void suspendAllProcessInstance() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3.查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();
        // 4.获取当前流程定义的实例是否都是挂起状态
        boolean flag = processDefinition.isSuspended();
        // 5.获取流程定义的ID
        String id = processDefinition.getId();
        // 6.判断是否挂起状态。是:改为激活;否:改为挂起
        if (flag) {
            // 改为激活. 参数1:流程定义的ID,参数2:是否激活,参数3:激活时间
            repositoryService.activateProcessDefinitionById(id, true, null);
            System.out.println("流程定义ID：" + id + "已激活");
        } else {
            // 改为挂起. 参数1:流程定义的ID;参数2:是否挂起;参数3:挂起时间
            repositoryService.suspendProcessDefinitionById(id, true, null);
            System.out.println("流程定义ID：" + id + "已挂起");
        }
    }

    /**
     * 单个流程实例的挂起和激活
     */
    @Test
    public void suspendSingleProcessInstance() {
        // 1.获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取 RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 3.通过 RuntimeService 获取流程实例对象
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("17501")
                .singleResult();
        // 4.得到当前流程实例的暂停状态
        boolean flag = instance.isSuspended();
        // 5.获取流程实例的ID
        String instanceId = instance.getId();
        // 6.判断是否暂停。是:改为激活;否:改为暂停
        if (flag) {
            runtimeService.activateProcessInstanceById(instanceId);
            System.out.println("流程实例ID：" + instanceId + "已激活");
        } else {
            runtimeService.suspendProcessInstanceById(instanceId);
            System.out.println("流程实例ID：" + instanceId + "已暂停");
        }
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcessNew() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 流程变量map
        Map<String, Object> map = new HashMap<>();
        // 设置流程变量
        Evection evection = new Evection();
        evection.setDays(2);
        // 把流程变量的pojo放入map
        map.put("evection", evection);
        map.put("assignee0", "张三");
        map.put("assignee1", "李经理");
        map.put("assignee2", "王财务");
        map.put("assignee3", "赵总经理");
        runtimeService.startProcessInstanceByKey("myProcess_1", map);
    }

    /**
     * 完成任务
     */
    @Test
    public void completTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Evection evection = new Evection();
        evection.setDays(2);
        Map<String, Object> map = new HashMap<>();
        map.put("evection", evection);
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_2")
                .taskAssignee("王财务0")
                .singleResult();
        if (task != null) {
            String taskId = task.getId();
            // 完成任务
            taskService.complete(taskId, map);
        }
    }

    //排他网关 //并行网关 //包含网关 //事件网关

}
