package com.practice.redis.service;

import com.practice.redis.annotation.Lock;

/**
 * 测试锁
 */
public interface MyService {

    /**
     * 基于默认时间过期
     * @param id1
     * @param id2
     * @return
     */
    @Lock(name ="lock", key = "#id1 + \"-\" + #id2")
    String doA(String id1, String id2);

    String doB(String id1, String id2);
}
