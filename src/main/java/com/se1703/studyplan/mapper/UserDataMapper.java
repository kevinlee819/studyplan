package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.UserData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/10 16:07
 **/
@Repository
public class UserDataMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查找
     */

    /**
     * id 查找
     * @param id
     * @return
     */
    public UserData findOneById(String id){
        return mongoTemplate.findById(id, UserData.class, "user_data");
    }

    /**
     * userid 查找
     * @param UserId
     * @return
     */
    public List<UserData> findByUserId(String UserId){
        Query query = new Query();
        query.addCriteria(Criteria.where("creater_id").is(UserId));
        return mongoTemplate.find(query, UserData.class, "user_data");
    }

    /**
     * query直接查找
     * @param query
     * @return
     */
    public List<UserData> findByQuery(Query query){
        return mongoTemplate.find(query, UserData.class, "user_data");
    }

    /**
     * 增加
     */

    public boolean saveOne(UserData task){
        UserData res = mongoTemplate.insert(task, "user_data");
        return StringUtils.isNotBlank(res.getId());
    }

    public void saveMany(List<UserData> tasks){
        mongoTemplate.insert(tasks, "user_data");
    }

    /**
     * 删除
     */

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean deleteById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Long res = mongoTemplate.remove(query, UserData.class, "user_data").getDeletedCount();
        return res > 0;
    }

    public boolean deleteByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("creater_id").is(userId));
        Long res = mongoTemplate.remove(query, UserData.class, "user_data").getDeletedCount();
        return res > 0;
    }
}
