package com.middol.activiti_demo.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * Created by 蔡康生 on 2019/6/26 15:22
 */
public class LeaveActivitiTest {

    public ProcessEngine processEngine;
    /**
     * @Author 蔡康生
     * @Date 15:23 2019/6/26
     * @Param []
     * @return void
     * @Description 描述:生成表
     **/
    @Test
    public void createTable(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
       this.processEngine=processEngine;
    }
/**
 * @Author 蔡康生
 * @Date 15:29 2019/6/26
 * @Param []
 * @return void
 * @Description 描述:部署流程
 **/
    @Test
    public void deployProcess(){
        /**
         * 生成表
         */
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        /**
         * 部署流程
         */
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("leave.xml");//bpmn文件的名称
        builder.deploy();

        /**
         * 启动流程
         */
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("请假流程");//流程的名称，也可以使用ById来启动流程，用key执行的是最新版本

        /**
         * 查看任务节点
         */
        TaskService taskService = processEngine.getTaskService();
        //根据assignee（代理人）查询任务
        String assignee="zs";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();

        for (Task task : tasks) {
            System.out.println("taskId:"+task.getId()+
            ",taskName:"+task.getName()+",assignee:"+task.getAssignee()+",creatime:"+task.getCreateTime());
        }


    }
}
