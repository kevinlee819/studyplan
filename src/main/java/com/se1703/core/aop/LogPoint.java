package com.se1703.core.aop;

import java.lang.annotation.*;

/**
 * @author leekejin
 * @date 2020/9/14 9:34
 **/
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPoint {

    /**
     * 日志的消息内容。支持SpEL表达式，参数可以使用#号加参数名称，也可以使用#p加参数下标
     */
    String message();

    /**
     * 日志分类
     */
    String type() default "operation";

}
