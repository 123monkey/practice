package com.practice.redis.redis;

import com.practice.easyexcel.utils.StringUtils;
import com.practice.redis.LockExecutor;
import com.practice.redis.enums.LockType;
import com.practice.redis.handler.RedissonHandler;
import com.practice.redis.exception.ServiceBizException;
import com.practice.redis.constant.LockConstant;
import com.practice.redis.utils.KeyUtil;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyazhou
 */
public class RedisLockExecutorImpl implements LockExecutor<RLock> {

    @Autowired
    private RedissonHandler redissonHandler;

    @Value("${" + LockConstant.PREFIX + "}")
    private String prefix;

    private boolean lockCached = true;

    private volatile Map<String, RLock> lockMap = new ConcurrentHashMap<>();

    private volatile Map<String, RReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();

    @Override
    public RLock tryLock(LockType lockType, String name, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Exception {
        if (StringUtils.isEmpty(name)) {
            throw new ServiceBizException("Name is null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new ServiceBizException("key is null or empty");
        }
        //key获取
        String compositeKey = KeyUtil.getCompositeKey(prefix, name, key);
        return tryLock(lockType, compositeKey, leaseTime, waitTime, async, fair);
    }

    @Override
    public RLock tryLock(LockType lockType, String compositeKey, long leaseTime, long waitTime, boolean async, boolean fair) throws Exception {
        if (StringUtils.isEmpty(compositeKey)) {
            throw new ServiceBizException("Composite key is null or empty");
        }
        if (lockType != LockType.LOCK && fair) {
            throw new ServiceBizException("Fair lock of Redis isn't support for " + lockType);
        }
        // 检查redission是否启动
        redissonHandler.validateStartedStatus();
        if (async) {
            return invokeLockAsync(lockType, compositeKey, leaseTime, waitTime, fair);
        } else {
            return invokeLock(lockType, compositeKey, leaseTime, waitTime, fair);
        }
    }

    private RLock invokeLock(LockType lockType, String key, long leaseTime, long waitTime, boolean fair) throws InterruptedException {
        RLock lock = getLock(lockType, key, fair);
        boolean acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        return acquired ? lock : null;
    }

    private RLock getLock(LockType lockType, String key, boolean fair) {
        //基于缓存获取缓存数据
        if (lockCached) {
            return getCachedLock(lockType, key, fair);
        } else {
            return getNewLock(lockType, key, fair);
        }
    }

    //缓存不存在，则从redission中重新获取
    private RLock getNewLock(LockType lockType, String key, boolean fair) {
        RedissonClient redisson = redissonHandler.getRedisson();
        switch (lockType) {
            case LOCK:
                if (fair) {
                    return redisson.getFairLock(key);
                } else {
                    return redisson.getLock(key);
                }
            case READ_LOCK:
                return getCachedReadWriteLock(lockType, key, fair).readLock();
            case WRITE_LOCK:
                return getCachedReadWriteLock(lockType, key, fair).writeLock();
        }
        throw new ServiceBizException("无效redis的lock类型 " + lockType);
    }

    //基于读写锁缓存获取，如果不存在，则重新从redission中获取
    private RReadWriteLock getCachedReadWriteLock(LockType lockType, String key, boolean fair) {
        String newKey = key + "-" + "fair[" + fair + "]";
        RReadWriteLock readWriteLock = readWriteLockMap.get(newKey);
        if (readWriteLock == null) {
            RedissonClient redissonClient = redissonHandler.getRedisson();
            RReadWriteLock newReadWriteLock = redissonClient.getReadWriteLock(key);
            readWriteLock = readWriteLockMap.putIfAbsent(newKey, newReadWriteLock);
            if (readWriteLock == null) {
                readWriteLock = newReadWriteLock;
            }
        }
        return readWriteLock;
    }


    // 从缓存中尝试获取锁
    private RLock getCachedLock(LockType lockType, String key, boolean fair) {
        String newKey = lockType + "-" + key + "-" + "fair[" + fair + "]";
        //获取锁
        RLock lock = lockMap.get(newKey);
        if (lock == null) {
            RLock newLock = getNewLock(lockType, key, fair);
            lock = lockMap.putIfAbsent(newKey, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }
        return lock;
    }


    private RLock invokeLockAsync(LockType lockType, String compositeKey, long leaseTime, long waitTime, boolean fair) throws ExecutionException, InterruptedException {
        RLock lock = getLock(lockType, compositeKey, fair);
        boolean acquired = lock.tryLockAsync(waitTime, leaseTime, TimeUnit.MILLISECONDS).get();
        return acquired ? lock : null;
    }

    @Override
    public void unlock(RLock lock) throws Exception {
        if(redissonHandler.isStarted()) {
            if(lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
