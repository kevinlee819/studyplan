package com.se1703.core.exception;

import com.se1703.core.constant.HttpStatusCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


/**
 * 业务发生异常
 *
 * @author leekejin
 * @date 2020-9-3 15:27:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;

    public BusinessException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.resolve(HttpStatusCodeEnum.INTERNAL_SERVER_ERROR.getCode());
    }

    public BusinessException(HttpStatusCodeEnum httpStatusCodeEnum) {
        super(httpStatusCodeEnum.getDefaultContent());
        this.httpStatus = HttpStatus.resolve(httpStatusCodeEnum.getCode());
    }

    public BusinessException(String msg, HttpStatusCodeEnum httpStatusCodeEnum) {
        super(msg == null ? httpStatusCodeEnum.getDefaultContent() : msg);
        this.httpStatus = HttpStatus.resolve(httpStatusCodeEnum.getCode());
    }

    public Integer getCode() {
        return this.httpStatus.value();
    }

}