package com.se1703.core.constant;

/**
 * @author leekejin
 * @date 2020-9-2 10:51:52
 */
public class Constant {
    /** 管理员账号，不需要校验权限 */
    public static final String ADMIN = "admin";
    /** 数据权限过滤 */
    public static final String HEADER_TOKEN_NAME = "Authorization";
    /** 普通账号 */
    public static final String COMMON = "common";

//    1-正在完成
//    2-超时未完成
//    3-已经完成

    public static final Integer DOING = 1;
    public static final Integer TIME_OUT = 2;
    public static final Integer FINISH = 3;

}
