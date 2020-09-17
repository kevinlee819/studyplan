package com.se1703.studyplan.service;

import com.se1703.core.Utils.TimeUtils;
import com.se1703.core.exception.BusinessException;
import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.UserData;
import com.se1703.studyplan.entity.VOs.*;
import com.se1703.studyplan.mapper.UserDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author leekejin
 * @date 2020/9/10 16:08
 **/
@Service
public class UserDataService {
    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private TaskService taskService;

    /**
     * 保存最新提交的任务数据
     *
     * @param userDataInputVO
     * @return 保存的id
     */
    public String saveOne(UserDataInputVO userDataInputVO) {
        if (userDataInputVO == null) {
            return null;
        }
        UserData userData = new UserData();
        userData.setUserId(authService.getCurrentUser().getUserId());
        userData.setStartTime(userDataInputVO.getStartTime());
        userData.setEndTime(userDataInputVO.getEndTime());
        Task task = new Task();
        if (userDataInputVO.getTask() != null) {
            TaskVO taskVO = userDataInputVO.getTask();
            task.setId(taskVO.getId());
            task.setTaskName(taskVO.getTaskName());
            Task tmp = taskService.getTaskById(taskVO.getId());
            if (tmp != null){
                task.setTags(tmp.getTags());
            }
        }
        userData.setTask(task);
        String recordId = userDataMapper.saveOne(userData);
        if (recordId == null) {
            throw new BusinessException("无法存入数据！");
        }
        if (task == null) {
            throw new BusinessException("缺少任务数据！");
        }
        String res = taskService.saveRecord(recordId, task.getId());
        if (res.equals(Result.ERR_DATABASE_CANT_REFRESH)) {
            throw new BusinessException("无法刷新任务提交数据！");
        }
        return res;
    }


    /**
     * 查询用户在该日学习的任务数和学习时长
     *
     * @param timeVO
     * @return
     */
    public DailyData computeDailyLearnTime(TimeVO timeVO) {
        if (timeVO == null) {
            return null;
        }
        List<UserData> userDataList = userDataMapper.getRecordByDateAndUserId(timeVO.getStartTime(), timeVO.getEndTime(), authService.getCurrentUser().getUserId());
        if (userDataList == null || userDataList.isEmpty()) {
            return null;
        }
        DailyData dailyData = new DailyData();
        dailyData.setTaskNum(userDataList.size());
        Long learnTime = 0L;
        for (UserData userData : userDataList) {
            long min = TimeUtils.timeDiff(userData.getStartTime(), userData.getEndTime());
            learnTime += min;
        }
        dailyData.setLearnTime(learnTime);
        return dailyData;
    }

    /**
     * 任务时间分布，按任务名
     *
     * @param timeVO
     * @return
     */
    public List<TimeDistributionVO> timeDistribution(TimeVO timeVO) {
        if (timeVO == null) {
            return null;
        }
        List<UserData> userDataList = userDataMapper.getRecordByDateAndUserId(timeVO.getStartTime(), timeVO.getEndTime(), authService.getCurrentUser().getUserId());
        if (userDataList == null || userDataList.isEmpty()) {
            return null;
        }
        List<TimeDistributionVO> list = new ArrayList<>();
        for (UserData userData : userDataList) {
            TimeDistributionVO distributionVO = new TimeDistributionVO();
            distributionVO.setTaskId(userData.getTask().getId());
            distributionVO.setTaskName(userData.getTask().getTaskName());
            if (list.contains(distributionVO)) {
                int index = list.indexOf(distributionVO);
                list.get(index).setUseTime(list.get(index).getUseTime() + TimeUtils.timeDiff(userData.getStartTime(), userData.getEndTime()));
            } else {
                distributionVO.setUseTime(TimeUtils.timeDiff(userData.getStartTime(), userData.getEndTime()));
                list.add(distributionVO);
            }
        }
        return list;
    }

    /**
     * 月度时间分析
     *
     * @param timeVO
     * @return
     * @throws ParseException
     */
    public List<MonthAnalyze> monthAnalysis(TimeVO timeVO) throws ParseException {
        if (timeVO == null) {
            return null;
        }
        List<UserData> userDataList = userDataMapper.getRecordByDateAndUserId(timeVO.getStartTime(), timeVO.getEndTime(), authService.getCurrentUser().getUserId());
        if (userDataList == null || userDataList.isEmpty()) {
            return null;
        }
        List<MonthAnalyze> list = new ArrayList<>();
        for (UserData userData : userDataList) {
            //开始时间和结束时间在同一天
            if (TimeUtils.isSameDay(userData.getStartTime(), userData.getEndTime())) {
                MonthAnalyze monthAnalyze = new MonthAnalyze();
                monthAnalyze.setDay(new SimpleDateFormat("yyyy-MM-dd").parse(userData.getStartTime().toString()));
                Long between = TimeUtils.timeDiff(userData.getStartTime(), userData.getEndTime());
                if (list.contains(monthAnalyze)) {
                    int index = list.indexOf(monthAnalyze);
                    list.get(index).setLearnTime(list.get(index).getLearnTime() + between);
                } else {
                    monthAnalyze.setLearnTime(between);
                    list.add(monthAnalyze);
                }
                //开始时间和结束时间不在同一天
            } else {
                Long pre = TimeUtils.timeDiff(userData.getStartTime(), TimeUtils.genFullClockDate(userData.getStartTime()));
                Long next = TimeUtils.timeDiff(TimeUtils.genZeroClockDate(userData.getEndTime()), userData.getEndTime());
                MonthAnalyze preM = new MonthAnalyze();
                preM.setDay(new SimpleDateFormat("yyyy-MM-dd").parse(userData.getStartTime().toString()));
                MonthAnalyze nextM = new MonthAnalyze();
                nextM.setDay(new SimpleDateFormat("yyyy-MM-dd").parse(userData.getEndTime().toString()));
                if (list.contains(preM)) {
                    int index = list.indexOf(preM);
                    list.get(index).setLearnTime(list.get(index).getLearnTime() + pre);
                } else {
                    preM.setLearnTime(pre);
                    list.add(preM);
                }
                if (list.contains(nextM)) {
                    int index = list.indexOf(nextM);
                    list.get(index).setLearnTime(list.get(index).getLearnTime() + next);
                } else {
                    nextM.setLearnTime(next);
                    list.add(nextM);
                }
            }
        }
        return list;
    }


    public boolean delByTaskId(String taskId){
       return userDataMapper.delTaskRecord(taskId);
    }


    public List<UserData> getUserDataByUserId(String userId){
        return userDataMapper.findByUserId(userId);
    }
}

