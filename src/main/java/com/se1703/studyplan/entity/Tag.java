package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author leekejin
 * @date 2020/9/14 16:23
 **/
@Data
@Document(collection = "tag")
public class Tag {
    @Id
    private String id;

    @Field("tag_name")
    private String tagName;

    @Field("user_id")
    private String userId;
}
