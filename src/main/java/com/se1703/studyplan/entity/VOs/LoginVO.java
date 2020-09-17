package com.se1703.studyplan.entity.VOs;

import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/17 15:28
 **/
@Data
public class LoginVO {
    private WxUserInfo userInfo;
    private String code;
}
