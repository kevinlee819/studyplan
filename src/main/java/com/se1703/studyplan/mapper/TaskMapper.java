package com.se1703.studyplan.mapper;

import com.se1703.studyplan.entity.Task;
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
public class TaskMapper {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查找
     */

    /**
     * id 查找
     * @param id
     * @return
     */
    public Task findOneById(String id){
        return mongoTemplate.findById(id, Task.class, "task");
    }

    /**
     * userid 查找
     * @param UserId
     * @return
     */
    public List<Task> findByUserId(String UserId){
        Query query = new Query();
        query.addCriteria(Criteria.where("creater_id").is(UserId));
        return mongoTemplate.find(query, Task.class, "task");
    }

    /**
     * query直接查找
     * @param query
     * @return
     */
    public List<Task> findByQuery(Query query){
        return mongoTemplate.find(query, Task.class, "task");
    }

    /**
     * 增加
     */

    public boolean saveOne(Task task){
         Task res = mongoTemplate.insert(task, "task");
         return StringUtils.isNotBlank(res.getId());
    }

    public void saveMany(List<Task> tasks){
        mongoTemplate.insert(tasks, "task");
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
        Long res = mongoTemplate.remove(query, Task.class, "task").getDeletedCount();
        return res > 0;
    }

    public boolean deleteByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("creater_id").is(userId));
        Long res = mongoTemplate.remove(query, Task.class, "task").getDeletedCount();
        return res > 0;
    }



}
