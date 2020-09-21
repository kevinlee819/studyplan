package com.se1703.studyplan.service;

import com.se1703.core.entity.MongoPageHelper;
import com.se1703.core.entity.PageResult;
import com.se1703.studyplan.entity.User;
import com.se1703.studyplan.entity.VOs.PageVO;
import com.se1703.studyplan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/10 16:07
 **/
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private SignInLogService signInLogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MongoPageHelper mongoPageHelper;

    /**
     * 通过openId获得用户
     * @param openId
     * @return
     */
    public User getByOpenId(String openId){
        return userMapper.findByOpenId(openId);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    public String saveUser(User user){
        return userMapper.saveOne(user);
    }


    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public PageResult<User> getAllUser(PageVO pageVO){
        return userMapper.getAllUser(pageVO);
    }

    public boolean delUser(String userId){
        diaryService.delByUserId(userId);
        signInLogService.delByUserId(userId);
        tagService.delByUserId(userId);
        taskService.delByUserId(userId);
        userDataService.delByUserId(userId);
        boolean flag = userMapper.deleteOneByUserId(userId);
        return flag;
    }

}
