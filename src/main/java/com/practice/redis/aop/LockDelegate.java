package com.practice.redis.aop;

import com.practice.redis.enums.LockType;
import org.aopalliance.intercept.MethodInvocation;

public interface LockDelegate {

    Object invoke(MethodInvocation invocation, LockType lockType, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Throwable;
}
