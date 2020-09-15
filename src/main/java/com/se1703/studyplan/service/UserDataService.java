package com.se1703.studyplan.service;

import com.se1703.core.exception.BusinessException;
import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.UserData;
import com.se1703.studyplan.entity.VOs.TaskVO;
import com.se1703.studyplan.entity.VOs.UserDataInputVO;
import com.se1703.studyplan.mapper.UserDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     *  保存最新提交的任务数据
     * @param userDataInputVO
     * @return 保存的id
     */
    public String saveOne(UserDataInputVO userDataInputVO){
        if(userDataInputVO == null){
            return null;
        }
        UserData userData = new UserData();
        userData.setUserId(authService.getCurrentUser().getUserId());
        userData.setStartTime(userDataInputVO.getStartTime());
        userData.setEndTime(userDataInputVO.getEndTime());
        Task task = new Task();
        if(userDataInputVO.getTask() != null) {
            TaskVO taskVO = userDataInputVO.getTask();
            task.setId(taskVO.getId());
            task.setTaskName(taskVO.getTaskName());
            if (taskVO.getTags() != null && !taskVO.getTags().isEmpty()){
                List<Tag> tags = new ArrayList<>();
                for (String tagName: taskVO.getTags()) {
                    Tag e = new Tag();
                    e.setTagName(tagName);
                    tags.add(e);
                }
                task.setTags(tags);
            }
        }
        userData.setTask(task);
        String recordId = userDataMapper.saveOne(userData);
        if (recordId == null){
            throw new BusinessException("无法存入数据！");
        }
        if (task == null){
            throw new BusinessException("缺少任务数据！");
        }
        boolean res = taskService.saveRecord(recordId,task.getId());
        if (!res){
            throw new BusinessException("无法刷新任务提交数据！");
        }
        return recordId;
    }


}
