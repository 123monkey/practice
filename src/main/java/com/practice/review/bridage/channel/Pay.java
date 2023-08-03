package com.practice.review.bridage.channel;

import com.practice.review.bridage.model.IPayMode;

import java.math.BigDecimal;

/**
 * 支付
 */
public abstract class Pay {

    protected IPayMode payMode;

    public Pay(IPayMode payMode) {
        this.payMode = payMode;
    }

    public abstract String transfer(String uid, String traceId, BigDecimal amount);
}
