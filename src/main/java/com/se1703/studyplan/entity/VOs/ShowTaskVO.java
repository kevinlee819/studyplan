package com.se1703.studyplan.entity.VOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author leekejin
 * @date 2020/9/17 9:38
 **/
@Data
public class ShowTaskVO {

    private String id;

    private String taskName;

    @ApiModelProperty(value = "0-单次，1-多次")
    private Integer taskType;

    @ApiModelProperty(value = "应该要提交的次数")
    private Integer times;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    private List<String> tags;

    @ApiModelProperty(value = "已经提交的次数")
    private Integer count;

    private String imgUrl;

    @ApiModelProperty(value = "是否提醒,0-否,1-是")
    private Integer isRemind;

    @ApiModelProperty(value = "1-正在完成,2-超时未完成,3-已经完成")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Integer minute;

}
