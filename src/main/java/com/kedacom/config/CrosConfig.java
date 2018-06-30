package com.kedacom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CROS全局跨域请求配置
 * package: com.kedacom.config
 * author: yaorui
 * date : 2018/6/29
 * Copyright @ Corporation 2018
 * 苏州科达科技   版权所有
 */
@Configuration
public class CrosConfig extends WebMvcConfigurerAdapter {

    /**
     * 详细配置：
     *      addMapping：请求的api路径              示例：/api/**
     *      allowedOrigins：请求IP                 示例：http://192.168.1.97，http://localhost:8080
     *      allowedMethod：请求方式
     *      allowCredentials：是否支持用户凭证
     *      maxAge：请求响应时间
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "UPDATE","DELETE")
                .allowCredentials(false).maxAge(3600);
    }
}
