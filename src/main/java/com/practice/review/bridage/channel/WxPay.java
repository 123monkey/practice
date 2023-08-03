package com.practice.review.bridage.channel;

import com.practice.review.bridage.model.IPayMode;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 微信支付
 */
@Slf4j
public class WxPay extends  Pay {

    public WxPay(IPayMode payMode) {
        super(payMode);
    }

    @Override
    public String transfer(String uid, String traceId, BigDecimal amount) {
        log.info("模拟微信支付");

        return null;
    }

}
