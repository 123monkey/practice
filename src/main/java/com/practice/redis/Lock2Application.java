package com.practice.redis;

import com.practice.redis.enums.LockType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

/**
 * 基于redission实现，需要编写lock和unlock代码块,基于lock
 */
@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.practice.redis.*"})
public class Lock2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Lock2Application.class, args);
        LockExecutor<Object> lockExecutor = applicationContext.getBean(LockExecutor.class);
        for (int i = 0; i < 5 ; i++) {
            new Thread(()->{
               Object lock = null;
               try{
                   lock = lockExecutor.tryLock(LockType.LOCK, "lock", "X-Y", 5000L, 60000L, false, false);
                   //如果拿到了锁，则执行业务操作
                   if(lock !=null) {
                       System.out.println("执行一些业务操作");
                       TimeUnit.MILLISECONDS.sleep(2000L);
                       log.info("doA - lock is got");
                   }
               }catch (Exception e) {
                   e.printStackTrace();
               }finally {
                  try{
                      lockExecutor.unlock(lock);
                  }catch (Exception e) {
                      e.printStackTrace();
                  }
               }
            }).start();
        }


        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Object lock = null;
                try {
                    lock = lockExecutor.tryLock(LockType.LOCK, "lock", "X-Y", 5000L, 60000L, false, false);
                    if (lock != null) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        log.info("doC - lock is got");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lockExecutor.unlock(lock);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    
}
