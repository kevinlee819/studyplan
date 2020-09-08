package com.se1703.core.constant;

import lombok.Getter;

/**
 * 业务异常时常用状态码
 * @author 吴淑超
 */
public enum HttpStatusCodeEnum {
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    @Getter
    private int code;
    @Getter
    private String defaultContent;

    HttpStatusCodeEnum(int code, String defaultContent) {
        this.code = code;
        this.defaultContent = defaultContent;
    }

}