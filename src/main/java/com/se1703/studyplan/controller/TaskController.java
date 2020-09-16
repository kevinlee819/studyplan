package com.se1703.studyplan.controller;

import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.VOs.CreateTaskVO;
import com.se1703.studyplan.entity.VOs.UserDataInputVO;
import com.se1703.studyplan.service.TaskService;
import com.se1703.studyplan.service.UserDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String createTask(CreateTaskVO taskVO){
        return taskService.createTask(taskVO);
    }

    @PostMapping("/submitRecord")
    @ApiOperation(value = "提交一次任务记录")
    public String submitRecord(UserDataInputVO entity){
        return userDataService.saveOne(entity);
    }

    @GetMapping("/getTask")
    @ApiOperation(value = "获得该用户所有任务列表")
    public List<Task> getTask(){
        return taskService.getUserTask();
    }

}
