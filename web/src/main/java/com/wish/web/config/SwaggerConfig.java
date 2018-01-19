package com.wish.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置
 * Created by wish on 2018/1/19.
 */
@Configuration  //说明这个是spring的设置
@EnableWebMvc   //不是SpringBoot需要引入这个
@EnableSwagger2 //开启Swagger2
@ComponentScan("com.wish.web.controller")  //指定被扫描Controller的位置
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)  //Docket，Springfox的私有API设置初始化为Swagger2
                .select()
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()  //设置API文档的主体说明
                        .title("framework")
                        .description("framework")
                        .version("1.0")
                        .termsOfServiceUrl("http://localhost:8080/")
                        .build());
    }

}
