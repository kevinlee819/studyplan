package com.se1703.studyplan.entity.VOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期，即ddl")
    private Date endTime;

    @ApiModelProperty(value = "字符串类型的学习标签列表")
    private List<String> tags;

    @ApiModelProperty(value = "背景图url")
    private String imgUrl;

    @ApiModelProperty(value = "是否提醒")
    private Integer isRemind;


}
