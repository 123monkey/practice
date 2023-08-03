package com.practice.review.newfactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class JDKProxy {

    /**
     * 获取代理对象
     * @param interfaceClass
     * @param cacheAdapter
     * @param <T>
     * @return
     */
    public static  <T> T get(Class<T> interfaceClass, ICacheAdapter cacheAdapter) {
        InvocationHandler handler = new JDKInvocationHandler(cacheAdapter);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] classes = interfaceClass.getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{classes[0]},handler);
    }

}
