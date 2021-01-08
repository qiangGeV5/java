package com.zq.config;


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

//添加扫描
@Configuration
@EnableSwagger2
public class Swagger2 {

    //http://localhost:8088/swagger-ui.html   原路径
    //http://localhost:8088/doc.html   原路径

    //配置swagger2核心配置
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)    //指定api类型为SWAGGER_2
                .apiInfo(apiInfo())                         //文件信息
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.zq.controller"))//指定controller包
                .paths(PathSelectors.any())               //所有controller包
                .build();



    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("电商平台api文档")
                .contact(new Contact("zq","https://","zq@126.com"))
                .description("电商平台api文档所有api都在")
                .version("1.0.0")
                .termsOfServiceUrl("www.baidu.com")
                .build();
    }


}
