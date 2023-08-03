package com.practice.redis.redis;

import com.practice.redis.handler.RedissonHandler;
import com.practice.redis.handler.RedissonHandlerImpl;
import com.practice.redis.utils.RedissonUtil;
import org.redisson.config.Config;

import java.io.IOException;

public class RedissonAdapterImpl implements RedissonAdapter {
    @Override
    public RedissonHandler getRedissonHandler() {
        // 来自远程配置中心的内容
        String yamlConfigContent = "...";

        Config config = null;
        try {
            config = RedissonUtil.createYamlConfig(yamlConfigContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String jsonConfigContent = "...";

        Config config = null;
        try {
            config = RedissonUtil.createJsonConfig(jsonConfigContent);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return new RedissonHandlerImpl(config);
    }
}