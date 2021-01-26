package com.zq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = "com.zq.mapper")
//扫描所有包，以及相关组件包
@ComponentScan(basePackages = {"com.zq","org.n3r.idworker"})
@EnableScheduling //开启定时任务
@EnableRedisHttpSession//开启使用redis作为spring session
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
