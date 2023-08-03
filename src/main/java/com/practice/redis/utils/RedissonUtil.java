package com.practice.redis.utils;

import com.practice.redis.AquariusContent;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

import java.io.IOException;

@Slf4j
public class RedissonUtil {

    // 创建Yaml格式的配置文件
    public static Config createYamlFileConfig(String yamlConfigPath) throws IOException {
        log.info("Start to read {}...", yamlConfigPath);

        AquariusContent content = new AquariusContent(yamlConfigPath, "UTF-8");

        return createYamlConfig(content.getContent());
    }

    // 创建Json格式的配置文件
    public static Config createJsonFileConfig(String jsonConfigPath) throws IOException {
        log.info("Start to read {}...", jsonConfigPath);

        AquariusContent content = new AquariusContent(jsonConfigPath, "UTF-8");

        return createJsonConfig(content.getContent());
    }

    // 创建Yaml格式的配置文件
    public static Config createYamlConfig(String yamlConfigContent) throws IOException {
        return Config.fromYAML(yamlConfigContent);
    }

    // 创建Json格式的配置文件
    public static Config createJsonConfig(String jsonConfigContent) throws IOException {
        return Config.fromJSON(jsonConfigContent);
    }
}