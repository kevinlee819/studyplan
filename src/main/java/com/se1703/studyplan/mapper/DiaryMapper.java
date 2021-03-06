package com.se1703.studyplan.mapper;

import com.se1703.core.Utils.MongoUtils;
import com.se1703.core.Utils.TimeUtils;
import com.se1703.studyplan.entity.Diary;
import com.se1703.studyplan.entity.VOs.ShowDiary;
import com.se1703.studyplan.entity.VOs.TimeVO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    public List<Diary> getDiaryByDate(Date startDate, Date endDate, String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id")
                .gte(MongoUtils.date2ObjectId(startDate))
                .lte(MongoUtils.date2ObjectId(endDate))
                .and("user_id").is(userId));
        return mongoTemplate.find(query,Diary.class,"diary");
    }

    public boolean updateDiary(ShowDiary showDiary){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(showDiary.getId()));
        Update update = new Update();
        update.set("title",showDiary.getTitle());
        update.set("content",showDiary.getContent());
        return mongoTemplate.updateFirst(query,update,Diary.class,"diary").getModifiedCount() > 0;
    }

    public boolean delByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.remove(query,Diary.class,"diary").getDeletedCount() > 0;
    }
}
