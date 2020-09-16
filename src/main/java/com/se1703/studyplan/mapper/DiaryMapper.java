package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.net.CacheRequest;
import java.util.List;


/**
 * @author leekejin
 * @date 2020/9/16 15:38
 **/
@Repository
public class DiaryMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    public String saveOne(Diary diary){
        return mongoTemplate.insert(diary,"diary").getId();
    }

    public boolean deleteById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, Diary.class, "diary").getDeletedCount() > 0;
    }

    public List<Diary> findByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.find(query, Diary.class, "diary");
    }
}
