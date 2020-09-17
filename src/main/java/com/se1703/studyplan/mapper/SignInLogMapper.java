package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.SignInLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author leekejin
 * @date 2020/9/17 22:56
 **/
@Repository
public class SignInLogMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean upsertLog(SignInLog signInLog){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(signInLog.getUserId()));
        Update update = new Update();
        update.set("user_name", signInLog.getUserName());
        update.set("latest_log_time", signInLog.getLatestLogTime());
        update.set("user_id", signInLog.getUserId());
        return mongoTemplate.upsert(query,update,SignInLog.class,"sign_in_log").getModifiedCount() > 0;
    }

    public List<SignInLog> getAll(){
        return mongoTemplate.findAll(SignInLog.class,"sign_in_log");
    }

    public SignInLog getOneByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.findOne(query,SignInLog.class,"sign_in_log");
    }

    public boolean delByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.remove(query,SignInLog.class,"sign_in_log").getDeletedCount() > 0;
    }
}
