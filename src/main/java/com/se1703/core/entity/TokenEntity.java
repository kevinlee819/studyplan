package com.se1703.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author leekejin
 * @Date 2020-9-10 16:02:54
 */
@Data
public class TokenEntity {
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "角色")
    private String role;

}
