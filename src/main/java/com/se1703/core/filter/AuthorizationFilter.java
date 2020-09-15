package com.se1703.core.filter;

import com.se1703.core.UserContext;
import com.se1703.core.constant.Constant;
import com.se1703.core.entity.TokenEntity;
import com.se1703.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2020/9/14 10:05
 **/
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 公共权限的直接放行
        Object permit = request.getAttribute("permit");
        if (permit != null && (boolean) permit) {
            filterChain.doFilter(request, response);
            return;
        }
        // 校验权限,失败返回403
        boolean pass = checkAuthorization(request);
        if (pass) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "没有访问权限");
        }
    }

    private boolean checkAuthorization(HttpServletRequest request) {
        TokenEntity tokenEntity = UserContext.getUser();
        if (tokenEntity == null) {
            return false;
        }
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String role = tokenEntity.getRole();
        log.info("开始校验权限：{} {}  {}", method, uri, role);
        // 管理员不需要校验权限
        if (Constant.ADMIN.equals(role)) {
            return true;
        } else if (Constant.COMMON.equals(role)){
            //TODO  这里校验访问路径的权限
            Stream<String> commonUserGet = Stream.of("/**"); // TODO 待补充普通用户Get方法可访问的路径
            Stream<String> commonUserPost = Stream.of("/**"); // TODO 待补充普通用户Post方法可访问的路径
            PathMatcher pathMatcher = new AntPathMatcher();
            if("GET".equals(method)){
                return Stream.of(commonUserGet).flatMap(item -> item)
                        // request.getRequestURI() 匹配到公共资源返回true
                        .anyMatch(allow -> pathMatcher.match(allow, uri));
            } else if ("POST".equals(method)){
                return  Stream.of(commonUserPost).flatMap(item -> item)
                        // request.getRequestURI() 匹配到公共资源返回true
                        .anyMatch(allow -> pathMatcher.match(allow, uri));
            } else{
                new BusinessException("请求方法出现问题：method = " + method);
            }
        }

        return false;
    }


}
