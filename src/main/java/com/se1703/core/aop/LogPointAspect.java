package com.se1703.core.aop;

import com.se1703.core.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author leekejin
 * @date 2020/9/14 9:32
 **/
@Slf4j
@Aspect
@Component
public class LogPointAspect {

    @Around(value = "@annotation(logPoint)")
    public Object around(ProceedingJoinPoint joinPoint, LogPoint logPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        String message = getMessage(joinPoint, proceed, logPoint);
        log.info("{}日志切点：{}", logPoint.type(), message);
        return proceed;
    }

    /**
     * SpEL格式处理
     */
    private String getMessage(ProceedingJoinPoint joinPoint, Object result, LogPoint logPoint) {
        String message = logPoint.message();
        if (StringUtils.isBlank(message)) {
            return "";
        } else if (!message.contains("#")) {
            return message;
        }
        // 获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // SpEL解析
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 自定义result变量为返回的结果
        context.setVariable("result", result);
        context.setVariable("tokenEntity", UserContext.getUser());
        // 参数的名称列表
        String[] paramNames = discoverer.getParameterNames(method);
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                // 参数和变量的值
                context.setVariable(paramNames[i], joinPoint.getArgs()[i]);
                context.setVariable("p" + i, joinPoint.getArgs()[i]);
            }
        }
        return parser.parseExpression(message).getValue(context, String.class);
    }
}
