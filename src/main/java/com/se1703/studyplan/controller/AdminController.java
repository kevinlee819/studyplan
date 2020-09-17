package com.se1703.studyplan.controller;

import com.se1703.studyplan.entity.SignInLog;
import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.entity.User;
import com.se1703.studyplan.entity.VOs.CurrentUserVO;
import com.se1703.studyplan.entity.VOs.ShowTaskVO;
import com.se1703.studyplan.service.SignInLogService;
import com.se1703.studyplan.service.TagService;
import com.se1703.studyplan.service.TaskService;
import com.se1703.studyplan.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/15 21:28
 **/
@RequestMapping("/admin")
@RestController
@Api(tags = "数据管理")
public class AdminController {
    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SignInLogService signInLogService;

    @GetMapping("/createCommonTag")
    @ApiOperation(value = "新增共有标签")
    public String createCommonTag(String tagName){
        return tagService.createCommonTag(tagName);
    }

    @GetMapping("/deleteCommonTag")
    @ApiOperation(value = "删除共有标签")
    public boolean deleteCommonTag(String tagName){
        return tagService.delCommonTagByName(tagName);
    }

    @GetMapping("/getAllUser")
    @ApiOperation(value = "获得所有用户的信息")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getAllTaskByUserId")
    @ApiOperation(value = "获得用户所有的任务信息")
    public List<ShowTaskVO> getTask(String userId){
        return taskService.getTaskDetail(userId);
    }


    @GetMapping("/getAllTag")
    @ApiOperation(value = "获得所有TAG")
    public List<Tag> getAllTag(){
        return tagService.getAllTag();
    }

    @GetMapping("/getAllSignLog")
    @ApiOperation(value = "查看所有登录日志")
    public List<SignInLog> getAll(){
        return signInLogService.getAll();
    }

    @GetMapping("/getAllSignLog")
    @ApiOperation(value = "根据用户Id删除登录日志")
    public boolean delLogByUserId(String userId){
        return signInLogService.delByUserId(userId);
    }
}
