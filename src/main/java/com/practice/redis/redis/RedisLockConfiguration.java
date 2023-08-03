package com.practice.redis.redis;

import com.practice.redis.aop.LockDelegate;
import com.practice.redis.LockExecutor;
import com.practice.redis.handler.RedissonHandler;
import com.practice.redis.handler.RedissonHandlerImpl;
import com.practice.redis.constant.RedissonConstant;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisLockConfiguration {
    @Value("${" + RedissonConstant.PATH + ":" + RedissonConstant.DEFAULT_PATH + "}")
    private String redissonPath;

    @Autowired(required = false)
    private RedissonAdapter redissonAdapter;

    @Bean
    @Conditional(RedisLockCondition.class)
    public LockDelegate redisLockDelegate() {
        return new RedisLockDelegateImpl();
    }

    @Bean
    @Conditional(RedisLockCondition.class)
    public LockExecutor<RLock> redisLockExecutor() {
        return new RedisLockExecutorImpl();
    }

    @Bean
    @Conditional(RedisLockCondition.class)
    @ConditionalOnMissingBean
    public RedissonHandler redissonHandler() {
        if (redissonAdapter != null) {
            return redissonAdapter.getRedissonHandler();
        }

        return new RedissonHandlerImpl(redissonPath);
    }
}