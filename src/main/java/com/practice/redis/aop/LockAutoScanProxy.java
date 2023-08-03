package com.practice.redis.aop;

import com.nepxion.matrix.proxy.aop.DefaultAutoScanProxy;
import com.nepxion.matrix.proxy.mode.ProxyMode;
import com.nepxion.matrix.proxy.mode.ScanMode;
import com.practice.redis.annotation.Lock;
import com.practice.redis.annotation.ReadLock;
import com.practice.redis.annotation.WriteLock;

import java.lang.annotation.Annotation;


public class LockAutoScanProxy extends DefaultAutoScanProxy {
    private static final long serialVersionUID = -957037966342626931L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] methodAnnotations;

    public LockAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_METHOD_ANNOTATION_ONLY, ScanMode.FOR_METHOD_ANNOTATION_ONLY);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[] { "lockInterceptor" };
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getMethodAnnotations() {
        if (methodAnnotations == null) {
            methodAnnotations = new Class[] { Lock.class, ReadLock.class, WriteLock.class };
        }

        return methodAnnotations;
    }
}