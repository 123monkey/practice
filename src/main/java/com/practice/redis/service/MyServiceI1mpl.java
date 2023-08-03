package com.practice.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyazhou
 */
@Slf4j
@Service("myService1Impl")
public class MyServiceI1mpl implements MyService1 {

    @Override
    public String doC(String id1, String id2) {
        try {
            TimeUnit.MILLISECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("doC - lock is got");

        return "C";
    }

    @Override
    public String doD(String id1, String id2) {
        log.info("doD");

        return "D";
    }
}