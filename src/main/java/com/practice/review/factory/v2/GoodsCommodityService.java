package com.practice.review.factory.v2;

import com.alibaba.fastjson.JSON;
import com.practice.review.factory.resreq.DeliverReq;
import com.practice.review.factory.v1.GoodsService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 发送商品增强
 */
@Slf4j
public class GoodsCommodityService implements ICommodity {

    private GoodsService goodsService = new GoodsService();

    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMap) {
        DeliverReq deliverReq = new DeliverReq();
        deliverReq.setUserName(queryUserName(uid));
        deliverReq.setSku(commodityId);
        deliverReq.setOrderId(bizId);
        deliverReq.setUserPhone(queryUserPhoneNumber(uid));
        deliverReq.setConsigneeUserName(extMap.get("consigneeUserName"));
        deliverReq.setConsigneeUserPhone(extMap.get("consigneeUserPhone"));
        deliverReq.setConsigneeUserAddress(extMap.get("consigneeUserAddress"));

        Boolean isSuccess = goodsService.deliverGoods(deliverReq);
        log.info("当前发放的商品信息：isSuccess:{},deliverReq:{}",isSuccess, JSON.toJSONString(deliverReq));
    }

    private String queryUserName(String uId) {
        return "花花";
    }

    private String queryUserPhoneNumber(String uId) {
        return "15200101232";
    }
}
