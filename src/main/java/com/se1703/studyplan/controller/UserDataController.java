package com.se1703.studyplan.controller;


import com.se1703.core.Utils.MongoUtils;
import com.se1703.studyplan.entity.VOs.*;
import com.se1703.studyplan.service.AuthService;
import com.se1703.studyplan.service.DiaryService;
import com.se1703.studyplan.service.TagService;
import com.se1703.studyplan.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/getDailyData")
    @ApiOperation(value = "获得时间范围内的任务数和学习时长")
    public DailyData dailyData(@RequestBody TimeVO timeVO) throws ParseException {
        return userDataService.computeDailyLearnTime(timeVO);
    }

    @PostMapping("/timeDistribution")
    @ApiOperation(value = "获得时间范围内的任务时间分布")
    public List<TimeDistributionVO> getDistribution(@RequestBody TimeVO timeVO) throws ParseException {
        return userDataService.timeDistribution(timeVO);
    }

    @PostMapping("/monthAnalyze")
    @ApiOperation(value = "获得时间范围内的月度时间分布")
    public List<MonthAnalyze> monthAnalyze(@RequestBody TimeVO timeVO) throws ParseException {
        return userDataService.monthAnalysis(timeVO);
    }

    @PostMapping("/addDiary")
    @ApiOperation(value = "增加日记")
    public String addDiary(@RequestBody CreateDiary createDiary){
       return diaryService.addDiary(createDiary);
    }

    @GetMapping("/getTotalDiary")
    @ApiOperation(value = "获得日记")
    public List<ShowDiary> getDiary(){
        return diaryService.getDiary();
    }

    @GetMapping("/delDiaryById")
    @ApiModelProperty(value = "根据id删除日记")
    public boolean delDiary(String id){
        return diaryService.delById(id);
    }

    @PostMapping("/updateDiaryById")
    @ApiModelProperty(value = "根据id修改日记")
    public boolean updateDiary(@RequestBody ShowDiary showDiary){
        return diaryService.updateDiary(showDiary);
    }


    @GetMapping("/getTags")
    @ApiOperation(value = "获得用户的Tag和共有Tag")
    public List<String> monthAnalyze() {
       return new ArrayList<>(TagService.getStringTags(tagService.getUserTag(authService.getCurrentUser().getUserId())));
    }

    @PostMapping("/getDiaryByDate")
    @ApiOperation(value = "按时间范围获得日记")
    public List<ShowDiary> getDiaryByDate(@RequestBody TimeVO timeVO) throws ParseException {
        return diaryService.getDiary(timeVO);
    }


    @GetMapping("/getTagDistribution")
    @ApiOperation(value = "获得标签时间分布")
    public List<TagDistribution> getTagDistribution(){
        return tagService.getTagDistribution();
    }

}
