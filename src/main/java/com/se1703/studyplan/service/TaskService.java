package com.se1703.studyplan.service;

import com.se1703.core.exception.BusinessException;
import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.VOs.CreateTaskVO;
import com.se1703.studyplan.entity.VOs.Result;
import com.se1703.studyplan.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author leekejin
 * @date 2020/9/10 16:08
 **/
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private TagService tagService;

    /**
     * 保存task
     * @param taskVO
     * @return
     */
    public String createTask(CreateTaskVO taskVO){
        if (taskVO == null){
            throw new BusinessException("task 空数据");
        }
        Task task = new Task();
        BeanUtils.copyProperties(task,taskVO);
        String userId = authService.getCurrentUser().getUserId();
        task.setUserId(userId);
        task.setStatus(1);
        // 下面对数据库内不存在的tag进行新增
        Set<String> tagSetNew = new HashSet<>(taskVO.getTags());
        Set<String> tagSetOld = TagService.getStringTags(tagService.getUserTag(userId));
        for (String s : tagSetNew) {
            if (!tagSetOld.contains(s)){
                Tag tag = new Tag();
                tag.setTagName(s);
                tag.setUserId(userId);
                tagService.saveTag(tag);
            }
        }
        task.setTags(tagService.getUserTag(userId));
       return taskMapper.saveOne(task);
    }



    /**
     * 保存任务提交数据
     * @param recordId
     * @param taskId
     * @return
     */
    public String saveRecord(String recordId, String taskId){
        int times = taskMapper.countRecord(taskId);
        int maxtimes = taskMapper.findOneById(taskId).getTimes();
        if(!taskMapper.saveRecord(recordId,taskId)){
            return Result.ERR_DATABASE_CANT_REFRESH;
        }
        if (times+1 >= maxtimes){
            if (taskMapper.updateStatus(3,taskId)){
                return Result.MISSION_COMPLETED;
            } else {
                return Result.ERR_DATABASE_CANT_REFRESH;
            }
        }
        return Result.OK;
    }

    public List<Task> getUserTask(){
        return taskMapper.findByUserId(authService.getCurrentUser().getUserId());
    }


}
