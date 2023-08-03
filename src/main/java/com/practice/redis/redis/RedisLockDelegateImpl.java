package com.practice.redis.redis;

import com.practice.redis.LockExecutor;
import com.practice.redis.aop.LockDelegate;
import com.practice.redis.constant.LockConstant;
import com.practice.redis.enums.LockType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.locks.Lock;

/**
 * 基于Redission实现分布式锁
 * @author liuyazhou
 */
@Slf4j
public class RedisLockDelegateImpl implements LockDelegate {

    @Autowired
    private LockExecutor<RLock> lockExecutor;

    @Value("${"+ LockConstant.LOCK_AOP_EXCEPTION_IGNORE+":"+"true}")
    private Boolean lockAopExcecutorIgnore;

    @Override
    public Object invoke(MethodInvocation invocation, LockType lockType, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Throwable {
        RLock lock = null;
       try{
           //调用redission的锁执行处理
           lock = lockExecutor.tryLock(lockType, key, leaseTime,waitTime, async, fair);
           if(lock!=null) {
               try{
                   return  invocation.proceed();
               }catch (Exception e) {
                   throw e;
               }
           }
       }catch (Exception e) {
           if(lockAopExcecutorIgnore) {
               log.error("Redis exception occurs while Lock, but still to proceed the invocation", e);
               return  invocation.proceed();
           }else {
               throw  e;
           }
       }finally {
           lockExecutor.unlock(lock);
       }
       if (lockAopExcecutorIgnore) {
           log.error("acquired redis lock failed, but still to processed in invocation");
           return invocation.proceed();
       } else {
           throw  new SecurityException("acquired redis lock failed, stop the processed  in invocation");
       }
    }
}
