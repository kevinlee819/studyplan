package com.se1703.studyplan.controller;


import com.se1703.core.Utils.MongoUtils;
import com.se1703.studyplan.entity.VOs.*;
import com.se1703.studyplan.service.AuthService;
import com.se1703.studyplan.service.DiaryService;
import com.se1703.studyplan.service.TagService;
import com.se1703.studyplan.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/data")
@RestController
@Api(tags = "数据查询接口")
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AuthService authService;

    @GetMapping("/getDailyData")
    @ApiOperation(value = "获得时间范围内的任务数和学习时长")
    public DailyData dailyData(TimeVO timeVO){
        return userDataService.computeDailyLearnTime(timeVO);
    }

    @GetMapping("/timeDistribution")
    @ApiOperation(value = "获得时间范围内的任务时间分布")
    public List<TimeDistributionVO> getDistribution(TimeVO timeVO){
        return userDataService.timeDistribution(timeVO);
    }

    @GetMapping("/monthAnalyze")
    @ApiOperation(value = "获得时间范围内的月度时间分布")
    public List<MonthAnalyze> monthAnalyze(TimeVO timeVO) throws ParseException {
        return userDataService.monthAnalysis(timeVO);
    }

    @PostMapping("/addDiary")
    @ApiOperation(value = "增加日记")
    public String addDiary(CreateDiary createDiary){
       return diaryService.addDiary(createDiary);
    }

    @PostMapping("/getDiary")
    @ApiOperation(value = "获得日记")
    public List<ShowDiary> getDiary(){
        return diaryService.getDiary();
    }

    @GetMapping("/getTags")
    @ApiOperation(value = "获得用户的Tag和共有Tag")
    public List<String> monthAnalyze() {
       return new ArrayList<>(TagService.getStringTags(tagService.getUserTag(authService.getCurrentUser().getUserId())));
    }


}
