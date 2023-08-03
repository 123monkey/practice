package com.practice.review.newfactory;

import java.util.concurrent.TimeUnit;

/**
 * 缓存适配接口
 */
public interface ICacheAdapter {

    String get(String key);

    void set(String key,String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}
