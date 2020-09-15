package com.se1703.studyplan.service;

import com.se1703.studyplan.entity.User;
import com.se1703.studyplan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author leekejin
 * @date 2020/9/10 16:07
 **/
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

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
    public boolean saveUser(User user){
        return userMapper.saveOne(user);
    }
}