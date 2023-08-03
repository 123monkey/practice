package com.practice.review.newfactory;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 提供类似于redis的功能，目前的一套功能方法
 */
@Slf4j
public class EGM {

    private Map<String,String> dataMap = new ConcurrentHashMap<>();

    public String gain(String key) {
        log.info("EGM获取数据 key:{}",key);
        return  dataMap.get(key);
    }

    public void  set(String key, String value) {
        log.info("EGM写入数据 key:{}, value:{}",key,value);
        dataMap.put(key,value);
    }

    public void  setEx(String key, String value,long timeout, TimeUnit timeUnit) {
        log.info("EGM写入数据 key:{}, value:{},timeout:{},timeUnit:{}",key,value,timeout,timeUnit);
        dataMap.put(key,value);
    }

    public void  delete(String key) {
        log.info("EGM写入数据 key:{}",key);
        dataMap.remove(key);
    }

}
