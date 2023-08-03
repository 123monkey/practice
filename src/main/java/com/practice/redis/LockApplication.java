package com.practice.redis;

import com.practice.redis.service.MyService;
import com.practice.redis.service.MyService1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 基于redission实现锁操作,基于过期机制，同时使用基于注解实现
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.practice.redis.*"})
public class LockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LockApplication.class, args);
        MyService myService = applicationContext.getBean(MyService.class);
        for (int i = 0; i < 5 ; i++) {
           new Thread(()->myService.doA("X","Y")).start();
        }
        MyService1 myService1 = applicationContext.getBean(MyService1.class);
        for (int i = 0; i < 5 ; i++) {
            new Thread(()->myService1.doC("X","Y")).start();
        }
    }
}
