package com.practice.review.factory.v2;

import java.util.Map;

/**
 * 统一接口提供对外
 */
public interface ICommodity {

    void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMa);
}
