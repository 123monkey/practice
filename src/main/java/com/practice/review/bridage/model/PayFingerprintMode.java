package com.practice.review.bridage.model;

import lombok.extern.slf4j.Slf4j;

/**
 * 指纹支付
 */
@Slf4j
public class PayFingerprintMode implements IPayMode {

    public boolean security(String uid) {
     log.info("当前指纹支付安全");
     return  true;
    }
}
