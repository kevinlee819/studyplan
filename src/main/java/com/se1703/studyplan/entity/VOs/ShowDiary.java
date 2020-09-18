package com.se1703.studyplan.entity.VOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020/9/16 15:49
 **/
@Data
public class ShowDiary {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
}
