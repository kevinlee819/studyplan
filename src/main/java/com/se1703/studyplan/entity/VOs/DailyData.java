package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/16 9:57
 **/
@Data
public class DailyData {
    @ApiModelProperty(value = "完成任务数")
    private Integer taskNum;

    @ApiModelProperty(value = "学习时间，单位：分钟")
    private Long learnTime;
}
