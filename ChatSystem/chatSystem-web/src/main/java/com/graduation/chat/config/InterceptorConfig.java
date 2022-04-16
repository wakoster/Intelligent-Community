package com.graduation.chat.config;

import com.graduation.chat.config.intercepors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 访问权限控制
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 允许匿名登录的地址
         */
        List<String> patterns = new ArrayList<>();
        patterns.add("/error");

//        patterns.add("/**");
//        patterns.add("/chat/**");
//        patterns.add("/chatSystem/beginChat");
        /**
         * 排除资源文件
         */
        List<String> exclude = Collections.singletonList("/static/**");
        /**
         * 配置拦截器
         */
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(patterns).excludePathPatterns(exclude);
    }
}
