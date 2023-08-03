package com.practice.review.factory.v1;

import com.alibaba.fastjson.JSON;
import com.practice.review.factory.resreq.DeliverReq;

/**
 * 商品下发
 */
public class GoodsService {
    public Boolean deliverGoods(DeliverReq req) {
        System.out.println("模拟发货实物商品一个：" + JSON.toJSONString(req));
        return true;
    }
}
