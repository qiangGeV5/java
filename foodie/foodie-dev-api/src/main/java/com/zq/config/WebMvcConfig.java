package com.zq.config;

import com.zq.interceptor.UserTokenInterceptor;
import com.zq.utils.JsonUtils;
import com.zq.utils.ZQJSONResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")  // 映射swagger2
                .addResourceLocations("file:/Users/admin/Desktop/image/");  // 映射本地静态资源
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    public UserTokenInterceptor userTokenInterceptor(){

        return new  UserTokenInterceptor();
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/Hello")
                .addPathPatterns("/hello")
                .addPathPatterns("/shopcart/add")
                .addPathPatterns("/shopcart/del")
                .addPathPatterns("/address/list")
                .addPathPatterns("/address/add")
                .addPathPatterns("/address/update")
                .addPathPatterns("/address/setDefalut")
                .addPathPatterns("/address/delete")
                .addPathPatterns("/orders/*")
                .addPathPatterns("/center/*")
                .addPathPatterns("/userInfo/*")
                .addPathPatterns("/myorders/*")
                .addPathPatterns("/mycomments/*")
                .excludePathPatterns("/myorders/deliver")
                .excludePathPatterns("/orders/notifyMerchantOrderPaid");
//        .addPathPatterns("/*/*")
//        .excludePathPatterns("/myorders/deliver");//设置不需要拦截的
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
