package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author leekejin
 * @date 2020/9/15 22:21
 **/
public class UpdateAdminPwd {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6, max = 32, message = "请输入密码长度为6 - 32")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPwd;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 32, message = "请输入密码长度为6 - 32")
    @ApiModelProperty(value = "新密码", required = true)
    private String newOwd;
}
