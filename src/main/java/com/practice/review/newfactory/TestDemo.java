package com.practice.review.newfactory;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 基于适配+动态代理实现
 */
@Slf4j
public class TestDemo {

   @Test
    public void testNewFactory(){
       CacheService egmServiceProxy = JDKProxy.get(CacheServiceImpl.class, new EGMCacheAdapter());
       // 获取EGMCacheAdapter对应的服务
      egmServiceProxy.set("1234","测试动态代理+适配");
      String val01 = egmServiceProxy.get("1234");
      log.info("va01的相关信息: val01:{}", JSON.toJSONString(val01));


   }
}
