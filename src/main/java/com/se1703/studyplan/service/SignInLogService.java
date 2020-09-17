package com.se1703.studyplan.service;

import com.se1703.studyplan.entity.SignInLog;
import com.se1703.studyplan.mapper.SignInLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/17 23:13
 **/
@Service
public class SignInLogService {
    @Autowired
    private AuthService authService;

    @Autowired
    private SignInLogMapper signInLogMapper;

    public boolean upsertLog(){
        SignInLog signInLog = new SignInLog();
        signInLog.setUserId(authService.getCurrentUser().getUserId());
        signInLog.setUserName(authService.getCurrentUser().getUserName());
        signInLog.setLatestLogTime(new Date());
        return signInLogMapper.upsertLog(signInLog);
    }

    public List<SignInLog> getAll(){
        return signInLogMapper.getAll();
    }

    public SignInLog getByUserId(String userId){
        return signInLogMapper.getOneByUserId(userId);
    }
    public boolean delByUserId(String userId){
        return signInLogMapper.delByUserId(userId);
    }

}
