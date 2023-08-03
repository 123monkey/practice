package com.practice.review.factory.v2;

import com.alibaba.fastjson.JSON;
import com.practice.review.factory.v1.IQiYiCardService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 卡券业务实现 对原来爱奇艺的业务进行增强
 */
@Slf4j
public class CardCommodityService implements ICommodity {

    private IQiYiCardService iQiYiCardService = new IQiYiCardService();

    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMap) {
         String mobile =  queryUserMobile(uid);
         iQiYiCardService.grantToken(mobile, bizId);
         log.info("当前请求爱奇艺的相关信息：{}", JSON.toJSONString(uid), JSON.toJSONString(commodityId));
    }

    private String queryUserMobile(String uId) {
        return "15200101232";
    }
}
