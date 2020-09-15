package com.se1703.studyplan.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author leekejin
 * @date 2020/9/14 16:20
 **/
@Data
public class TaskSubmitRecord {
    @Id
    private String id;
}
