package com.zq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//跨域问题
@Configuration
public class CorsConfig {

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        //1添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080"); //可以写星号 ===  *
        config.addAllowedOrigin("http://34.80.74.194:8080"); //可以写星号 ===  *
        config.addAllowedOrigin("http://34.80.74.194:8080"); //可以写星号 ===  *
        config.addAllowedOrigin("http://34.80.74.194"); //可以写星号 ===  *
        config.addAllowedOrigin("http://34.80.74.194"); //可以写星号 ===  *
        config.addAllowedOrigin("*");


//        serverUrl: "http://34.80.74.194:8088/foodie-dev-api",                      // 接口服务接口地址
//                paymentServerUrl: "http://payment.t.mukewang.com/foodie-payment",       // 支付中心服务地址
//                shopServerUrl: "http://34.80.74.194:8080/foodie-shop/",                            // 门户网站地址
//                centerServerUrl: "http://34.80.74.194:8080/foodie-center/",                        // 用户中心地址
//                cookieDomain: ".80.74.194;",                                       // cookie 域

        //设置是否发送cookie信息
        config.setAllowCredentials(true);

        //设置允许的请求方式
        config.addAllowedMethod("*");

        //设置允许的header
        config.addAllowedHeader("*");

        //2、为url添加映射路径
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",config);

        //3、返回重新定义的corsSouce
        return new CorsFilter(urlBasedCorsConfigurationSource);


    }
}
