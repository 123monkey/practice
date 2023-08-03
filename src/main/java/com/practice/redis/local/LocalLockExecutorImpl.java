package com.practice.redis.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.practice.redis.LockExecutor;
import com.practice.redis.enums.LockType;
import com.practice.redis.exception.ServiceBizException;
import com.practice.redis.constant.LockConstant;
import com.practice.redis.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;


public class LocalLockExecutorImpl implements LockExecutor<Lock> {
    @Value("${" + LockConstant.PREFIX + "}")
    private String prefix;

    // 可重入锁可重复使用
    private volatile Map<String, Lock> lockMap = new ConcurrentHashMap<String, Lock>();
    private volatile Map<String, ReadWriteLock> readWriteLockMap = new ConcurrentHashMap<String, ReadWriteLock>();
    private boolean lockCached = true;

    @Override
    public Lock tryLock(LockType lockType, String name, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new ServiceBizException( "Name is null or empty");
        }

        if (StringUtils.isEmpty(key)) {
            throw new ServiceBizException("Key is null or empty");
        }

        String compositeKey = KeyUtil.getCompositeKey(prefix, name, key);

        return tryLock(lockType, compositeKey, leaseTime, waitTime, async, fair);
    }

    @Override
    public Lock tryLock(LockType lockType, String compositeKey, long leaseTime, long waitTime, boolean async, boolean fair) throws Exception {
        if (StringUtils.isEmpty(compositeKey)) {
            throw new ServiceBizException("Composite key is null or empty");
        }

        if (async) {
            throw new ServiceBizException("Async lock of Local isn't support for " + lockType);
        }

        Lock lock = getLock(lockType, compositeKey, fair);
        boolean acquired = lock.tryLock(waitTime, TimeUnit.MILLISECONDS);

        return acquired ? lock : null;
    }

    @Override
    public void unlock(Lock lock) throws Exception {
        if (lock != null) {
            if (lock instanceof ReentrantLock) {
                ReentrantLock reentrantLock = (ReentrantLock) lock;
                // 只有ReentrantLock提供isLocked方法
                if (reentrantLock.isLocked()) {
                    reentrantLock.unlock();
                }
            } else {
                lock.unlock();
            }
        }
    }

    private Lock getLock(LockType lockType, String key, boolean fair) {
        if (lockCached) {
            return getCachedLock(lockType, key, fair);
        } else {
            return getNewLock(lockType, key, fair);
        }
    }

    private Lock getNewLock(LockType lockType, String key, boolean fair) {
        switch (lockType) {
            case LOCK:
                return new ReentrantLock(fair);
            case READ_LOCK:
                return getCachedReadWriteLock(lockType, key, fair).readLock();
            case WRITE_LOCK:
                return getCachedReadWriteLock(lockType, key, fair).writeLock();
        }

        throw new ServiceBizException("Invalid Local lock type for " + lockType);
    }

    private Lock getCachedLock(LockType lockType, String key, boolean fair) {
        String newKey = lockType + "-" + key + "-" + "fair[" + fair + "]";

        Lock lock = lockMap.get(newKey);
        if (lock == null) {
            Lock newLock = getNewLock(lockType, key, fair);
            lock = lockMap.putIfAbsent(newKey, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }

        return lock;
    }

    private ReadWriteLock getCachedReadWriteLock(LockType lockType, String key, boolean fair) {
        String newKey = key + "-" + "fair[" + fair + "]";

        ReadWriteLock readWriteLock = readWriteLockMap.get(newKey);
        if (readWriteLock == null) {
            ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock(fair);
            readWriteLock = readWriteLockMap.putIfAbsent(newKey, newReadWriteLock);
            if (readWriteLock == null) {
                readWriteLock = newReadWriteLock;
            }
        }

        return readWriteLock;
    }
}