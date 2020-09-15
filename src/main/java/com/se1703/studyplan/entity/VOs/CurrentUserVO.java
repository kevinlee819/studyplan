package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author leekejin
 * @date 2020/9/15 14:48
 **/
@Data
public class CurrentUserVO {
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty(value = "用户角色", required = true)
    private String role;
}
