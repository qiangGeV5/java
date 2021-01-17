package com.zq.config;




import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
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

    // 定义分隔符,配置Swagger多包
    private static final String SPLITOR = ";";

    //配置swagger2核心配置
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)    //指定api类型为SWAGGER_2
                .apiInfo(apiInfo())                         //文件信息
                .select()
                .apis(scanBasePackage("com.zq.controller"+SPLITOR+
                        "com.zq.controller.center"))//指定controller包
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


    /**
     * 切割扫描的包生成Predicate<RequestHandler>
     * @param basePackage
     * @return
     */
    public static Predicate<RequestHandler> scanBasePackage(final String basePackage) {
        if(StringUtils.isBlank(basePackage)){
            throw new NullPointerException("basePackage不能为空，多个包扫描使用"+SPLITOR+"分隔");
        }
        String[] controllerPack = basePackage.split(SPLITOR);
        Predicate<RequestHandler> predicate = null;
        for (int i = controllerPack.length -1; i >= 0 ; i--) {
            String strBasePackage = controllerPack[i];
            if(StringUtils.isNotBlank(strBasePackage)){
                Predicate<RequestHandler> tempPredicate = RequestHandlerSelectors.basePackage(strBasePackage);
                predicate = predicate == null ? tempPredicate : Predicates.or(tempPredicate,predicate);
            }
        }
        if(predicate == null){
            throw new NullPointerException("basePackage配置不正确，多个包扫描使用"+SPLITOR+"分隔");
        }
        return predicate;
    }


}
