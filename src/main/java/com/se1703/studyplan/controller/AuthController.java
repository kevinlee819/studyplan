package com.se1703.studyplan.controller;


import com.se1703.studyplan.entity.VOs.WxUserInfo;
import com.se1703.studyplan.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login" , produces = "application/json")
    @ApiOperation(value = "传入userInfo和code")
    public String createJwt(WxUserInfo userInfo,
                            @RequestParam(required = true,value = "code")String wxCode) throws Exception {
        return authService.generateToken(userInfo,authService.getWeSession(wxCode));
    }

}
