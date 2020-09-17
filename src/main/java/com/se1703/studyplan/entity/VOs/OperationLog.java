package com.se1703.studyplan.entity.VOs;

import lombok.Data;

import java.util.Date;

/**
 * @author leekejin
 * @date 2020/9/17 22:48
 **/
@Data
public class OperationLog {
    private String method;

    private Date date;

}
