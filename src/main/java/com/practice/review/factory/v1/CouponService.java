package com.practice.review.factory.v1;

import com.practice.review.factory.resreq.CouponResult;

/**
 * 优惠券
 */
public class CouponService {


    public CouponResult sendCoupon(String uId, String couponNumber, String uuid) {
        System.out.println("模拟发放优惠券一张：" + uId + "," + couponNumber + "," + uuid);
        return new CouponResult("0000", "发放成功");
    }
}
