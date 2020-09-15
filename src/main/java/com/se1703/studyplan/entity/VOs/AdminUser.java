package com.se1703.studyplan.entity.VOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
/**
 * @author leekejin
 * @date 2020/9/15 16:53
 **/
@Data
public class AdminUser {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "请输入密码长度为6 - 32")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
