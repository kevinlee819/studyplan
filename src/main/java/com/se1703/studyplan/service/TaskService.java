package com.se1703.studyplan.service;

import com.se1703.core.Utils.MongoUtils;
import com.se1703.core.Utils.TimeUtils;
import com.se1703.core.constant.Constant;
import com.se1703.core.exception.BusinessException;
import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.entity.Task;
import com.se1703.studyplan.entity.VOs.CreateTaskVO;
import com.se1703.studyplan.entity.VOs.Result;
import com.se1703.studyplan.entity.VOs.ShowTaskVO;
import com.se1703.studyplan.entity.VOs.TaskVO;
import com.se1703.studyplan.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.*;

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

    @Autowired
    private UserDataService userDataService;

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
        BeanUtils.copyProperties(taskVO,task);
        String userId = authService.getCurrentUser().getUserId();
        task.setUserId(userId);
        if (taskVO.getTimes() != null && taskVO.getTimes() > 0){
            task.setStatus(Constant.DOING);
        } else {
            task.setStatus(Constant.FINISH);
        }
        // 下面对数据库内不存在的tag进行新增
        Set<String> tagSetNew = new HashSet<>();
        if (taskVO.getTags()!= null && !taskVO.getTags().isEmpty()){
            for (String tag : taskVO.getTags()) {
                tagSetNew.add(tag);
            }
        }
        Set<String> tagSetOld = TagService.getStringTags(tagService.getUserTag(userId));
        if (!tagSetNew.isEmpty()){
            for (String s : tagSetNew) {
                if (!tagSetOld.contains(s)){
                    Tag tag = new Tag();
                    tag.setTagName(s);
                    tag.setUserId(userId);
                    tagService.saveTag(tag);
                }
            }
        }
        task.setTags(taskVO.getTags());
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
            if (taskMapper.updateStatus(Constant.FINISH,taskId)){
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

    public List<ShowTaskVO> getTaskDetail(){
        return getTaskDetail(authService.getCurrentUser().getUserId());
    }

    public List<ShowTaskVO> getTaskDetail(String userId){
        List<Task> tasks = taskMapper.findByUserId(userId);
        if (tasks == null || tasks.isEmpty()){
            return null;
        }
        List<ShowTaskVO> taskVOS = new ArrayList<>();
        for (Task task : tasks) {
            System.out.println(task);
            ShowTaskVO taskVO = new ShowTaskVO();
            BeanUtils.copyProperties(task,taskVO);
            if (task.getRecords() != null){
                taskVO.setCount(task.getRecords().size());
            }
            taskVO.setTags(task.getTags());
            taskVO.setCreateTime(MongoUtils.objectId2Date(task.getId()));
            System.out.println(taskVO);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }


    public boolean delTask(String taskId){
        //删除任务记录和提交记录
        boolean flag = taskMapper.deleteById(taskId);
        userDataService.delByTaskId(taskId);
        return flag;
    }

    public Task getTaskById(String taskId){
        return taskMapper.findOneById(taskId);
    }

    public void refreshTask(){
        List<Task> tasks = taskMapper.findAll();
        for (Task task : tasks) {
            if (task.getRecords() != null && !task.getRecords().isEmpty()){
                if (task.getTimes() != null && task.getTimes() > 0){
                    if (task.getRecords().size() >= task.getTimes()){
                        taskMapper.updateStatus(Constant.FINISH,task.getId());
                    }
                }
            }
            if (task.getEndTime() != null){
                Calendar ddl = Calendar.getInstance();
                ddl.setTime(task.getEndTime());
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                if (now.after(ddl)){
                    taskMapper.updateStatus(Constant.TIME_OUT,task.getId());
                }
            }
        }
    }


}
