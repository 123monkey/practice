package com.practice.redis.handler;

import com.practice.redis.exception.ServiceBizException;
import com.practice.redis.utils.RedissonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * redission处理器实现
 */
@Slf4j
public class RedissonHandlerImpl implements RedissonHandler {

    private RedissonClient redisson;

    public RedissonHandlerImpl(String yamlConfigPath) {
        try {
            Config config = RedissonUtil.createYamlFileConfig(yamlConfigPath);

            initialize(config);
        } catch (Exception e) {
            log.error("Initialize Redisson failed", e);
        }
    }

    public RedissonHandlerImpl(Config config) {
        try {
            initialize(config);
        } catch (Exception e) {
            log.error("Initialize Redisson failed", e);
        }
    }

    // 创建Redisson
    private void initialize(Config config) {
        create(config);
    }

    // 使用config创建Redisson
    private void create(Config config) {
        log.info("Start to initialize Redisson...");

        if (redisson != null) {
            throw new ServiceBizException("Redisson isn't null, it has been initialized already");
        }

        redisson = Redisson.create(config);
    }

    // 关闭Redisson客户端连接
    @Override
    public void close() {
        log.info("Start to close Redisson...");

        validateStartedStatus();

        redisson.shutdown();
    }

    // 获取Redisson客户端是否初始化
    @Override
    public boolean isInitialized() {
        return redisson != null;
    }

    // 获取Redisson客户端连接是否正常
    @Override
    public boolean isStarted() {
        if (redisson == null) {
            throw new ServiceBizException("Redisson isn't initialized");
        }

        return !redisson.isShutdown() && !redisson.isShuttingDown();
    }

    // 检查Redisson是否是启动状态
    @Override
    public void validateStartedStatus() {
        if (redisson == null) {
            throw new ServiceBizException("Redisson isn't initialized");
        }

        if (!isStarted()) {
            throw new ServiceBizException("Redisson is closed");
        }
    }

    // 检查Redisson是否是关闭状态
    @Override
    public void validateClosedStatus() {
        if (redisson == null) {
            throw new ServiceBizException("Redisson isn't initialized");
        }

        if (isStarted()) {
            throw new ServiceBizException("Redisson is started");
        }
    }

    // 获取Redisson客户端
    @Override
    public RedissonClient getRedisson() {
        return redisson;
    }
}
