package com.ssm.file.interceptor;

import com.ssm.file.entity.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器：未登录访问受保护资源时重定向到登录页。
 * 放行路径在 WebMvcConfig#addInterceptors 中配置。
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("loginUser");
            Object username = session.getAttribute("username");
            if (user instanceof SysUser || (username instanceof String && !((String) username).isEmpty())) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
