package com.practice.redis.local;

import com.practice.redis.aop.LockDelegate;
import com.practice.redis.LockExecutor;
import com.practice.redis.enums.LockType;
import com.practice.redis.exception.ServiceBizException;
import com.practice.redis.constant.LockConstant;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.locks.Lock;

public class LocalLockDelegateImpl implements LockDelegate {
    private static final Logger LOG = LoggerFactory.getLogger(LocalLockDelegateImpl.class);

    @Autowired
    private LockExecutor<Lock> lockExecutor;

    @Value("${" + LockConstant.LOCK_AOP_EXCEPTION_IGNORE + ":true}")
    private Boolean lockAopExceptionIgnore;

    @Override
    public Object invoke(MethodInvocation invocation, LockType lockType, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Throwable {
        Lock lock = null;
        try {
            lock = lockExecutor.tryLock(lockType, key, leaseTime, waitTime, async, fair);
            if (lock != null) {
                try {
                    return invocation.proceed();
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            if (lockAopExceptionIgnore) {
                LOG.error("Exception occurs while Lock, but still to proceed the invocation", e);

                return invocation.proceed();
            } else {
                throw e;
            }
        } finally {
            lockExecutor.unlock(lock);
        }

        if (lockAopExceptionIgnore) {
            LOG.error("Acquired lock failed, but still to proceed the invocation");

            return invocation.proceed();
        } else {
            throw new ServiceBizException("Acquired lock failed, stop to proceed the invocation");
        }
    }
}