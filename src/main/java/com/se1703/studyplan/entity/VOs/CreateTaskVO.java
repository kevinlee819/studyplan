package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/15 20:06
 **/
@Data
public class CreateTaskVO {
    @ApiModelProperty(value = "任务名")
    private String taskName;

    @ApiModelProperty(value = "任务类型，0-单次，1-多次")
    private Integer taskType;

    @ApiModelProperty(value = "要完成的学习次数")
    private Integer times;

    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @ApiModelProperty(value = "结束日期，即ddl")
    private Date endTime;

    @ApiModelProperty(value = "字符串类型的学习标签列表")
    private List<String> tags;

}
