package com.practice.redis;

import com.practice.redis.local.LocalLockConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LocalLockConfiguration.class })
public class LockConfiguration {

}