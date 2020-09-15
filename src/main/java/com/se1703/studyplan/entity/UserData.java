package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020-9-10 16:06:22
 */
@Data
@Document(collection = "user_data")
public class UserData {
    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("task")
    private Task task;

    @Field("start_time")
    private Date startTime;

    @Field("end_time")
    private Date endTime;

}
