package com.se1703.studyplan.controller;


import com.se1703.studyplan.entity.VOs.AdminUser;
import com.se1703.studyplan.entity.VOs.CurrentUserVO;
import com.se1703.studyplan.entity.VOs.LoginVO;
import com.se1703.studyplan.entity.VOs.WxUserInfo;
import com.se1703.studyplan.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/auth")
@RestController
@Api(tags = "用户管理")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "传入userInfo和code")
    public String createJwt(@RequestBody LoginVO loginVO) throws Exception {
        return authService.generateToken(loginVO.getUserInfo(),authService.getWeSession(loginVO.getCode()));
    }

    @GetMapping("/getCurrentUser")
    @ApiOperation(value = "获得当前用户")
    public CurrentUserVO getCurrentUser(){
        return authService.getCurrentUser();
    }

    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录入口")
    public String adminLogin(@Valid @RequestBody AdminUser entity){
        return authService.genAdminToken(entity);
    }


}
