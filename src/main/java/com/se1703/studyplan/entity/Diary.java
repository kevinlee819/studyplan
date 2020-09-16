package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author leekejin
 * @date 2020/9/16 15:32
 **/
@Data
@Document(collection = "diary")
public class Diary {
    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("title")
    private String title;

    @Field("content")
    private String content;
}
