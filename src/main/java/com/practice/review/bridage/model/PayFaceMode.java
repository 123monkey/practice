package com.practice.review.bridage.model;

import lombok.extern.slf4j.Slf4j;

/**
 * 人脸支付
 */
@Slf4j
public class PayFaceMode implements IPayMode {

    public boolean security(String uid) {
      log.info("人脸支付安全");
      return  true;
    }
}
