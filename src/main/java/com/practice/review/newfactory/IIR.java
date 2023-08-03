package com.practice.review.newfactory;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 实现对redis另一套集群进行操作
 */
@Slf4j
public class IIR {
    private Map<String,String> dataMap = new ConcurrentHashMap<>();

    public String get(String key) {
        log.info("IIR获取数据 key:{}", key);
        return dataMap.get(key);
    }

    public void set(String key,String value) {
        log.info("IIR设置的数据 key:{}, value:{}",key,value);
        dataMap.put(key, value);
    }

    public void setExprie(String key,String value,long timeout, TimeUnit timeUnit) {
        log.info("IIR设置的数据 key:{}, value:{}, timeout:{},timeUnit:{}",key,value,timeout,timeUnit);
        dataMap.put(key, value);
    }

    public void del(String key) {
        log.info("IIR设置的数据 key:{}",key);
        dataMap.remove(key);
    }

}
