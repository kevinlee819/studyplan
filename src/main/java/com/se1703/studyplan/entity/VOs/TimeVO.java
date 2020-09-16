package com.se1703.studyplan.entity.VOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020/9/16 9:16
 **/
@Data
public class TimeVO {

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    //"yyyy/MM/dd HH:mm:ss"
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
}
