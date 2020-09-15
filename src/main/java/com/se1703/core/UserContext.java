package com.se1703.core;

import com.se1703.core.entity.TokenEntity;

/**
 * 用户信息上下文
 * @author leekejin
 * @date 2020/9/14 9:33
 **/
public class UserContext {
    private static ThreadLocal<TokenEntity> threadLocal = new ThreadLocal<>();

    public static TokenEntity getUser() {
        return threadLocal.get();
    }

    public static void setUser(TokenEntity entity) {
        threadLocal.set(entity);
    }

    public static void removeUser() {
        threadLocal.remove();
    }
}
