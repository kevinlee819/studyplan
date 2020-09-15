package com.se1703.core.config;

import com.se1703.core.filter.AuthenticationFilter;
import com.se1703.core.filter.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leekejin
 * @date 2020/9/14 10:02
 **/
@Configuration
public class FilterConfig {
    /**
     * 认证过滤器注册
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthenticationFilter());
        registration.setName("AuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 在拦截器中需要用到查询数据库校验权限，不注册到Bean，注入为null
     */
    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }

    /**
     * 授权拦截器注册
     */
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationRegistration() {
        FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authorizationFilter());
        registration.setName("AuthorizationFilter");
        registration.setOrder(2);
        return registration;
    }
}
