package com.se1703.studyplan.controller;

import com.se1703.studyplan.entity.VOs.CurrentUserVO;
import com.se1703.studyplan.service.TagService;
import com.se1703.studyplan.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TaskService taskService;

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

}
