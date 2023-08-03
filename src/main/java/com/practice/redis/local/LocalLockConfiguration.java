package com.practice.redis.local;

import com.practice.redis.aop.LockDelegate;
import com.practice.redis.LockExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.Lock;

/**
 * 这里会将LockExecutor<?>注入到Spring中 重要 lock模式下bean使用
 */
@Configuration
public class LocalLockConfiguration {
    @Bean
    @Conditional(LocalLockCondition.class)
    public LockDelegate localLockDelegate() {
        return new LocalLockDelegateImpl();
    }

    @Bean
    @Conditional(LocalLockCondition.class)
    public LockExecutor<Lock> localLockExecutor() {
        return new LocalLockExecutorImpl();
    }
}