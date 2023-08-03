package com.practice.redis.local;

import com.practice.redis.AquariusCondition;
import com.practice.redis.constant.LockConstant;

public class LocalLockCondition extends AquariusCondition {
    public LocalLockCondition() {
        super(LockConstant.LOCK_TYPE, LockConstant.LOCK_TYPE_LOCAL);
    }
}