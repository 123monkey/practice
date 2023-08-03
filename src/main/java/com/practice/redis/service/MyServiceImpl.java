package com.practice.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyazhou
 */
@Slf4j
@Service("myServiceImpl")
public class MyServiceImpl implements MyService {

    @Override
    public String doA(String id1, String id2) {
        try {
            TimeUnit.MILLISECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("doA - lock is got");

        return "A";
    }

    @Override
    public String doB(String id1, String id2) {
        log.info("doB");

        return "B";
    }
}