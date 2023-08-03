package com.practice.redis.redis;

import com.practice.redis.AquariusCondition;
import com.practice.redis.constant.LockConstant;

public class RedisLockCondition extends AquariusCondition {
    public RedisLockCondition() {
        super(LockConstant.LOCK_TYPE, LockConstant.LOCK_TYPE_REDIS);
    }
}