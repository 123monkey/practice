package com.practice.review.newfactory;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务提供的统一接口
 */
public interface CacheService {

    String get(final  String key);

    void set(String key,String value);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void del(String key);
}
