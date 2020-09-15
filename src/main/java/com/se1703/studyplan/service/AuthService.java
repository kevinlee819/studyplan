package com.se1703.studyplan.service;


import com.alibaba.fastjson.JSON;
import com.se1703.core.UserContext;
import com.se1703.core.Utils.HttpRequest;
import com.se1703.core.Utils.JwtUtil;
import com.se1703.core.Utils.Sm3Utils;
import com.se1703.core.aop.LogPoint;
import com.se1703.core.constant.HttpStatusCodeEnum;
import com.se1703.core.entity.TokenEntity;
import com.se1703.core.exception.BusinessException;
import com.se1703.core.properties.WeAuth;
import com.se1703.studyplan.entity.User;
import com.se1703.studyplan.entity.VOs.AdminUser;
import com.se1703.studyplan.entity.VOs.CurrentUserVO;
import com.se1703.studyplan.entity.VOs.WxUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author leekejin
 * @date 2020-9-3 15:15:37
 */
@Slf4j
@Service
public class AuthService {
    @Autowired
    private WeAuth weAuth;

    @Autowired
    private UserService userService;

    /**
     * 根据小程序登录返回的code获取openid和session_key
     * https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html?t=20161107
     * @param weCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> getWeSession(String weCode) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(weAuth.getAppId());
        sb.append("&secret=").append(weAuth.getSecret());
        sb.append("&js_code=").append(weCode);
        sb.append("&grant_type=").append(weAuth.getGrantType());
        String res = HttpRequest.sendGet(weAuth.getSessionHost(), sb.toString());
        if(res == null || "".equals(res)){
            throw new BusinessException("未查询到该用户，请检查用户是否注册微信！");
        }
        return JSON.parseObject(res, Map.class);
    }

    /**
     *  生成Token
     * @param userInfo
     * @param wxSessionMap
     * @return
     * @throws BusinessException
     */
    @LogPoint(message = "用户登录：")
    public String generateToken(WxUserInfo userInfo, Map<String,Object> wxSessionMap) throws BusinessException {
        if(wxSessionMap.get("errcode") != null && !"0".equals(wxSessionMap.get("errcode").toString())){
            System.out.println(wxSessionMap.toString());
            throw new BusinessException("微信登录错误，错误码" + wxSessionMap.get("errcode").toString() +
                    ", 请查询https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html 错误码errcode");
        }
        System.out.println(wxSessionMap.toString());
        // 加密后存储
        String wxOpenId = Sm3Utils.hmac((String)wxSessionMap.get("openid"));
        String wxSessionKey = (String)wxSessionMap.get("session_key");

        // 若数据库中不存在这个用户
        User user = userService.getByOpenId(wxOpenId);
        if (null == user){
            if (null != userInfo){
                user = new User();
                user.setUserProsByInfo(userInfo);
                user.setOpenId(wxOpenId);
                // 存入数据库
                userService.saveUser(user);
            } else {
                throw new BusinessException("请用户授权！");
            }
        }
        return createTokenByUser(user);
    }

    /**
     * 根据user创建token
     * @param user
     * @return
     */
    private String createTokenByUser(User user){
        // 返回token
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserName(user.getUserName());
        tokenEntity.setUserId(user.getOpenId());
        tokenEntity.setRole(user.getUserRole());
        String token;
        try {
            token = JwtUtil.generate(tokenEntity);
        } catch (Exception e) {
            log.error("创建token失败:", e);
            e.printStackTrace();
            throw new BusinessException("创建token失败");
        }
        return token;
    }


    public CurrentUserVO getCurrentUser(){
        TokenEntity tokenEntity = UserContext.getUser();
        CurrentUserVO currentUserVO = new CurrentUserVO();
        if (tokenEntity == null) {
            throw new BusinessException("无法获取到tokenEntity");
        }
        currentUserVO.setRole(tokenEntity.getRole());
        //tokenEntity的UserId指的不是用户的id，而是openId
        currentUserVO.setUserId(userService.getByOpenId(tokenEntity.getUserId()).getId());
        currentUserVO.setUserName(tokenEntity.getUserName());
        return currentUserVO;
    }

    @LogPoint(message = "管理员登录：")
    public String genAdminToken(AdminUser entity){
        String account = entity.getUserName();
        String password = entity.getPassword();
        String key = Sm3Utils.hmac(account.concat("&").concat(password));
        System.out.println(key);
        User user = userService.getByOpenId(key);
        if (user == null) {
            throw new BusinessException("请检查用户名或密码", HttpStatusCodeEnum.BAD_REQUEST);
        }
        return createTokenByUser(user);
    }



}
