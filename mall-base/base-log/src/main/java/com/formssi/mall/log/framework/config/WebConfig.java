package com.formssi.mall.log.framework.config;

import com.formssi.mall.log.framework.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author echo
 * @date 2022年04月13日 11:59
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }
}
