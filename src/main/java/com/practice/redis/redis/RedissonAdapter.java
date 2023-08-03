package com.practice.redis.redis;

import com.practice.redis.handler.RedissonHandler;

public interface RedissonAdapter {
    RedissonHandler getRedissonHandler();
}