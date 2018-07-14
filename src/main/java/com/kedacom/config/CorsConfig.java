package com.kedacom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * @author wangyonghui
 * @description 域名配置类
 * @date 13:08 2018/6/29 0029
 **/
@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {

    /**
     * 详细配置：
     *      addMapping：请求的api路径
     *      allowedOrigins：请求IP
     *      allowedMethod：请求方式
     *      allowCredentials：是否支持用户凭证
     *      maxAge：请求响应时间
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }

}
