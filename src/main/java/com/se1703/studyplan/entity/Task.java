package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author leekejin
 * @date 2020-9-10 16:05:32
 */
@Data
@Document(collection = "task")
public class Task {
    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("task_name")
    private String taskName;

    @Field("task_type")
    private Integer taskType;

    @Field("times")
    private Integer times;

    @Field("start_time")
    private Date startTime;

    @Field("end_time")
    private Date endTime;

    @Field("tags")
    private List<Tag> tags;

    @Field("submit_records")
    private List<String> records;

    /**
     * 1-正在完成
     * 2-超时未完成
     * 3-已经完成
     */
    @Field("status")
    private Integer status;

}
