package com.practice.redis.service;

import com.practice.redis.annotation.Lock;

/**
 * 测试锁
 */
public interface MyService1 {

    @Lock(name ="lock", key = "#id1 + \"-\" + #id2")
    String doC(String id1, String id2);

    String doD(String id1, String id2);
}
