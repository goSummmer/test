package com.kedacom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by yr on 2018/09/13
 */
@EnableSwagger2
@Configuration
public class Swagger2 {

    /**
     *  创建Docket,并向springboot容器中注入Swagger组件，
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kedacom.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     *  构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Spring Boot中使用Swagger2构建RESTful APIs")
            .description("Swagger2 API文档。")
            .termsOfServiceUrl("http://localhost:8080")
            .contact(new Contact("YaoRui", "http://www.kedacom.com", "yaorui@kedacom.com"))
            .version("1.0")
            .build();
    }
}
