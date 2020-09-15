package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author leekejin
 * @date 2020-9-10 16:06:47
 */
@Repository
public class UserMapper  {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 查找操作
     */

    /**
     * 根据openId查找 user
     *
     * @param openId
     * @return
     */
    public User findByOpenId(String openId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("open_id").is(openId));
        return mongoTemplate.findOne(query,User.class,"user");
    }

    public User findByUserId(String id){
        return mongoTemplate.findById(id,User.class,"user");
    }

    /**
     * 自定义查找
     * @param query
     * @param tableName
     * @return
     */
    public List<User> findByQuery(Query query, String tableName){
        return mongoTemplate.find(query,User.class,"user");
    }

    /**
     * 保存操作
     */

    /**
     * 保存一个
     * @param user
     * @return
     */
    public boolean saveOne(User user){
        User res = mongoTemplate.insert(user,"user");
        return StringUtils.isNotBlank(res.getId());
    }

    /**
     * 保存多个
     * @param users
     * @return
     */
    public void saveMany(List<User> users){
        mongoTemplate.insert(users,"user");
    }

    /**
     * 删除操作
     */
    public boolean deleteOneByOpenId(String openId){
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(openId));
        Long count = mongoTemplate.remove(query,"user").getDeletedCount();
        return count > 0;
    }

    public boolean deleteOneByUserId(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Long count = mongoTemplate.remove(query,"user").getDeletedCount();
        return count > 0;
    }
}
