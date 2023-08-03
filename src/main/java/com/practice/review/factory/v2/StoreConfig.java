package com.practice.review.factory.v2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 进行初始化
 */
public class StoreConfig {

    protected static Map<Integer, ICommodity> storeMap = new ConcurrentHashMap<>();

    @Resource
    private CouponCommodityService couponCommodityService;

    @Resource
    private GoodsCommodityService goodsCommodityService;

    @Resource
    private CardCommodityService cardCommodityService;


    @PostConstruct
    public void init() {
        storeMap.put(1, couponCommodityService);
        storeMap.put(2, goodsCommodityService);
        storeMap.put(3, cardCommodityService);
    }
}
