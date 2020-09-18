package com.se1703.studyplan.controller;

import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.VOs.CreateTaskVO;
import com.se1703.studyplan.entity.VOs.ShowTaskVO;
import com.se1703.studyplan.entity.VOs.UpdateTaskVO;
import com.se1703.studyplan.entity.VOs.UserDataInputVO;
import com.se1703.studyplan.service.TaskService;
import com.se1703.studyplan.service.UserDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/task")
@RestController
@Api(tags = "任务管理")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserDataService userDataService;

    @PostMapping("/createTask")
    @ApiOperation(value = "创建一个任务，返回任务的id")
    public String createTask(@RequestBody CreateTaskVO taskVO){
        return taskService.createTask(taskVO);
    }

    @PostMapping("/submitRecord")
    @ApiOperation(value = "提交一次任务记录")
    public String submitRecord(@RequestBody UserDataInputVO entity){
        return userDataService.saveOne(entity);
    }

    @GetMapping("/getTask")
    @ApiOperation(value = "获得该用户所有任务列表")
    public List<ShowTaskVO> getTask(){
        return taskService.getTaskDetail();
    }

    @GetMapping("/deleteTask")
    @ApiOperation(value = "删除任务")
    public boolean delTask(String taskId){
        return taskService.delTask(taskId);
    }

    @GetMapping("/refreshTaskStatus")
    @ApiOperation(value = "刷新任务状态")
    public void refreshTaskStatus() {
        taskService.refreshTask();
    }

    @PostMapping("/updateTask")
    @ApiOperation(value = "更新任务")
    public boolean updateTask(@RequestBody UpdateTaskVO updateTaskVO){
        return taskService.updateTask(updateTaskVO);
    }



}
