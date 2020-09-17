package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/14 20:55
 **/
@Repository
public class TagMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据userId 查找tag
     *
     * @param userId
     * @return
     */
    public List<Tag> findByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.find(query,Tag.class, "tag");
    }

    public Tag findById(String id){
        return mongoTemplate.findById(id,Tag.class,"tag");
    }

    /**
     * 自定义查找
     * @param query
     * @return
     */
    public List<Tag> findByQuery(Query query){
        return mongoTemplate.find(query,Tag.class,"tag");
    }


    public boolean deleteByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        Long res = mongoTemplate.remove(query, Tag.class, "tag").getDeletedCount();
        return res > 0;
    }

    public boolean deleteByUserIdAndName(String userId, String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId).and("tag_name").is(name));
        Long res = mongoTemplate.remove(query, Tag.class, "tag").getDeletedCount();
        return res > 0;
    }

    public boolean deleteById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Long res = mongoTemplate.remove(query, Tag.class, "tag").getDeletedCount();
        return res > 0;
    }

    public String saveTag(Tag tag){
        return mongoTemplate.insert(tag,"tag").getId();
    }


    public List<Tag> getAllTag(){
        return mongoTemplate.findAll(Tag.class,"tag");
    }
}
