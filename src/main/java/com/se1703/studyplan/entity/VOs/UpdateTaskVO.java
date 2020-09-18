package com.se1703.studyplan.entity.VOs;

import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/18 14:25
 **/
@Data
public class UpdateTaskVO {
    private String id;
    private String taskName;
    private Integer taskType;
    private String imgUrl;
    private Integer isRemind;
    private Integer minute;
}
