package com.practice.redis.aop;

import com.practice.redis.aop.LockAutoScanProxy;
import com.practice.redis.aop.LockInterceptor;
import com.practice.redis.constant.LockConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockAopConfiguration {
    @Value("${" + LockConstant.LOCK_SCAN_PACKAGES + ":}")
    private String scanPackages;

    @Bean
    public LockAutoScanProxy lockAutoScanProxy() {
        return new LockAutoScanProxy(scanPackages);
    }

    @Bean
    public LockInterceptor lockInterceptor() {
        return new LockInterceptor();
    }
}