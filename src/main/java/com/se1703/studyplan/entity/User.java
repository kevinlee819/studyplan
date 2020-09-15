package com.se1703.studyplan.entity;

import com.se1703.studyplan.entity.VOs.WxUserInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * @author leekejin
 * @date 2020-9-10 16:06:00
 */
@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @Indexed
    @Field("open_id")
    private String openId;

    @Field("user_name")
    private String userName;

    @Field("sex")
    private Integer sex;

    @Field("user_role")
    private String userRole;

    @Field("avatar_url")
    private String avatarUrl;

    @Field("province")
    private String province;

//    public User setUserProsByMap(Map<String,String> map){
//        this.setUserRole("common");
//        this.setUserName(map.get("nickname"));
//        this.setSex(Integer.getInteger(map.get("gender")));
//        this.setAvatarUrl(map.get("avatarUrl"));
//        this.setOpenId(map.get("openId"));
//        //this.setBirth();
//        return this;
//    }

    public void setUserProsByInfo(WxUserInfo userInfo){
        this.setUserName(userInfo.getNickName());
        this.setSex(userInfo.getGender());
        this.setUserRole("common");
        this.setProvince(userInfo.getProvince());
        this.setAvatarUrl(userInfo.getAvatarUrl());
    }
}
