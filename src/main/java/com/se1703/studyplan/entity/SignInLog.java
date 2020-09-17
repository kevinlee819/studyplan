package com.se1703.studyplan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se1703.studyplan.entity.VOs.OperationLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020/9/16 15:36
 **/
@Data
@Document(collection = "sign_in_log")
public class SignInLog {
    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("user_name")
    private String userName;

    @ApiModelProperty(value = "最近一次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Field("latest_log_time")
    private Date latestLogTime;

}
