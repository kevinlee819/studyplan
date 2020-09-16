package com.se1703.studyplan.entity.VOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se1703.core.Utils.TimeUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author leekejin
 * @date 2020/9/16 11:08
 **/
@Data
public class MonthAnalyze {
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期")
    private Date day;

    @ApiModelProperty(value = "用时，单位分钟")
    private Long learnTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        MonthAnalyze that = (MonthAnalyze) o;
        return TimeUtils.isSameDay(day,that.day);
    }

    @SneakyThrows
    @Override
    public int hashCode() {
        return Objects.hash(new SimpleDateFormat("yyyy-MM-dd").parse(day.toString()));
    }
}
