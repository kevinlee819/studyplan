package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @author leekejin
 * @date 2020/9/16 10:27
 **/
@Data
public class TimeDistributionVO {
    @ApiModelProperty(value = "任务名")
    private String taskName;

    @ApiModelProperty(value = "任务Id")
    private String taskId;

    @ApiModelProperty(value = "用时，单位分钟")
    private Long useTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        TimeDistributionVO that = (TimeDistributionVO) o;
        return Objects.equals(taskName, that.taskName) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskId);
    }
}
