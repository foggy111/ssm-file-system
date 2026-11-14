package com.ssm.file.config;

import com.ssm.file.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 使用 WebMvcConfigurer#addInterceptors 注册拦截器，不使用 XML 的 mvc:interceptors。
 * 放行：登录页、登录接口、hello 页以及 CSS/JS/图片等静态资源。
 */
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/doLogin",
                        "/hello",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/static/**");
    }
}
