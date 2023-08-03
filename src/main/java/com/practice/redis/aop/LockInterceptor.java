package com.practice.redis.aop;

import com.nepxion.matrix.proxy.aop.AbstractInterceptor;
import com.practice.redis.annotation.Lock;
import com.practice.redis.annotation.ReadLock;
import com.practice.redis.annotation.WriteLock;
import com.practice.redis.constant.LockConstant;
import com.practice.redis.enums.LockType;
import com.practice.redis.exception.ServiceBizException;
import com.practice.redis.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 锁拦截器
 */
@Slf4j
public class LockInterceptor extends AbstractInterceptor {

    @Autowired
    private LockDelegate lockDelegate;


    @Value("${" + LockConstant.PREFIX + "}")
    private String prefix;

    @Value("${" + LockConstant.FREQUENT_LOG_PRINT + ":false}")
    private Boolean frequentLogPrint;

    /**
     * 进行拦截操作
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Lock lockAnnotation = getLockAnnotation(invocation);
        if (lockAnnotation != null) {
           String name = lockAnnotation.name();
           String key = lockAnnotation.key();
           long waitTime = lockAnnotation.waitTime();
           long leaseTime = lockAnnotation.leaseTime();
           boolean async = lockAnnotation.async();
           boolean fair = lockAnnotation.fair();

           return  invoke(invocation,lockAnnotation, name, key, leaseTime, waitTime, async, fair);
        }

        ReadLock readLockAnnotation = getReadLockAnnotation(invocation);
        if (readLockAnnotation != null) {
            String name = readLockAnnotation.name();
            String key = readLockAnnotation.key();
            long leaseTime = readLockAnnotation.leaseTime();
            long waitTime = readLockAnnotation.waitTime();
            boolean async = readLockAnnotation.async();
            boolean fair = readLockAnnotation.fair();

            return invoke(invocation, readLockAnnotation, name, key, leaseTime, waitTime, async, fair);
        }

        WriteLock writeLockAnnotation = getWriteLockAnnotation(invocation);
        if (writeLockAnnotation != null) {
            String name = writeLockAnnotation.name();
            String key = writeLockAnnotation.key();
            long leaseTime = writeLockAnnotation.leaseTime();
            long waitTime = writeLockAnnotation.waitTime();
            boolean async = writeLockAnnotation.async();
            boolean fair = writeLockAnnotation.fair();

            return invoke(invocation, writeLockAnnotation, name, key, leaseTime, waitTime, async, fair);
        }
        return null;
    }

    private Object invoke(MethodInvocation invocation, Annotation annotation, String name, String key, long leaseTime, long waitTime, boolean async, boolean fair) throws Throwable {
        LockType lockType = getLockType(annotation);
        if (lockType == null) {
            throw new ServiceBizException("Lock type is null for " + annotation);
        }
        String lockTypeValue = lockType.getValue();
        if (StringUtils.isBlank(name)) {
            throw new ServiceBizException("Annotation [" + lockTypeValue + "]'s name is null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new ServiceBizException("Annotation [" + lockTypeValue + "]'s key is null or empty");
        }
        String spelKey = null;
        try{
            spelKey = getSpelKey(invocation, key);
        }catch (Exception e){
            spelKey = key;
        }
        String compositeKey = KeyUtil.getCompositeKey(prefix, name, spelKey);
        String proxyType = getProxyType(invocation);
        String proxiedClassName = getProxiedClassName(invocation);
        String methodName = getMethodName(invocation);
        if (frequentLogPrint) {
            log.info("Intercepted for annotation - {} [key={}, leaseTime={}, waitTime={}, async={}, fair={}, proxyType={}, proxiedClass={}, method={}]", lockTypeValue, compositeKey, leaseTime, waitTime, async, fair, proxyType, proxiedClassName, methodName);
        }
        return lockDelegate.invoke(invocation,lockType, compositeKey, leaseTime, waitTime, async, fair);
    }

    private LockType getLockType(Annotation annotation) {
        if (annotation instanceof Lock) {
            return LockType.LOCK;
        }
        return null;
    }

    public Lock getLockAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(Lock.class)) {
            return method.getAnnotation(Lock.class);
        }
        return null;
    }

    private ReadLock getReadLockAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(ReadLock.class)) {
            return method.getAnnotation(ReadLock.class);
        }

        return null;
    }

    private WriteLock getWriteLockAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(WriteLock.class)) {
            return method.getAnnotation(WriteLock.class);
        }
        return null;
    }
}
