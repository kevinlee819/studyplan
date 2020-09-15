package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020/9/15 10:25
 **/
@Data
public class UserDataInputVO {

    private TaskVO task;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
