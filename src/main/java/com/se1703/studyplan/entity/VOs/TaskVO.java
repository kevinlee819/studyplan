package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/15 14:43
 **/
@Data
public class TaskVO {
    @ApiModelProperty(value = "taskId，即任务的id", required = true)
    private String id;

    @ApiModelProperty(value = "任务名", required = true)
    private String taskName;

    @ApiModelProperty(value = "标签名列表")
    private List<String> tags;
}
