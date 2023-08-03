package com.practice.review.factory.v2;

import com.practice.review.factory.resreq.CouponResult;
import com.practice.review.factory.v1.CouponService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * 优惠券实现的增强
 */
@Slf4j
public class CouponCommodityService implements ICommodity {

    private CouponService couponService = new CouponService();

    //进行接口统一之后的增强实现
    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMa) {
        CouponResult couponResult = couponService.sendCoupon(uid, commodityId, bizId);
        log.info("请求的优惠券：uid:{},commodityId:{},bizId:{}",uid, commodityId,bizId);
        if(Objects.equals("000",couponResult.getCode())) {
            throw  new RuntimeException(couponResult.getInfo());
        }
    }
}
