package com.se1703.core.filter;

import com.se1703.core.UserContext;
import com.se1703.core.Utils.JwtUtil;
import com.se1703.core.constant.Constant;
import com.se1703.core.entity.TokenEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author leekejin
 * @date 2020/9/14 10:04
 **/
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String OPTIONS = "OPTIONS";

    /**
     * 过滤不需要登录也能访问的资源
     *
     * @param request 请求
     * @return true允许访问，false需要登录
     */
    private boolean permit(HttpServletRequest request) {
        // 跨域的时候会有OPTIONS请求，直接放行
        if (OPTIONS.equalsIgnoreCase(request.getMethod())){
            return true;
        }
        // swagger
        Stream<String> swagger = Stream.of("/swagger-resources/**", "/swagger-ui.html", "/v2/**", "/webjars/**");
        // 不需要登录可以访问的公共资源
        Stream<String> common = Stream.of("/auth/login","/auth/adminLogin");
        // 校验请求request.getRequestURI()是否匹配
        PathMatcher pathMatcher = new AntPathMatcher();
        return Stream.of(swagger, common).flatMap(item -> item)
                // request.getRequestURI() 匹配到公共资源返回true
                .anyMatch(allow -> pathMatcher.match(allow, request.getRequestURI()));
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从header中获取jwtToken
        try {
            String token = request.getHeader(Constant.HEADER_TOKEN_NAME);
            if (StringUtils.isNotBlank(token)) {
                TokenEntity tokenEntity = JwtUtil.validation(token);
                // 使用上下文管理用户信息
                if (tokenEntity != null && tokenEntity.getUserId() != null) {
                    if (Constant.ADMIN.equals(tokenEntity.getRole()) || Constant.COMMON.equals(tokenEntity.getRole())){
                        // 登录账号的放行
                        try {
                            UserContext.setUser(tokenEntity);
                            filterChain.doFilter(request, response);
                        } finally {
                            UserContext.removeUser();
                        }
                        return;
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限");
                        return;
                    }

                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            e.printStackTrace();
            return;
        }
        // 不需要登录也可以访问的放行
        if (permit(request)) {
            request.setAttribute("permit", true);
            filterChain.doFilter(request, response);
            return;
        }
        // 不登录访问非公共资源
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未登陆");
    }

}
