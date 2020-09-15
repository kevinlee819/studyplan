package com.se1703.studyplan.entity.VOs;

import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/14 8:52
 **/
@Data
public class WxUserInfo {

    private String nickName;
    /**
    性别 0：未知、1：男、2：女
    */
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
}
