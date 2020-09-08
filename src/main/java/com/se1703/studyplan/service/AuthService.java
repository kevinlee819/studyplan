package com.se1703.studyplan.service;


import com.alibaba.fastjson.JSON;
import com.se1703.core.Utils.HttpRequest;
import com.se1703.core.exception.BusinessException;
import com.se1703.core.properties.WeAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author leekejin
 * @date 2020-9-3 15:15:37
 */
@Service
public class AuthService {
    @Autowired
    private WeAuth weAuth;


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
        if(res == null || res.equals("")){
            throw new BusinessException("未查询到该用户，请检查用户是否注册微信！");
        }
        return JSON.parseObject(res, Map.class);
    }




}
