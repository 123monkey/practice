package com.practice.review.bridage.model;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于密码支付
 */
@Slf4j
public class PayCypher implements IPayMode{

    public boolean security(String uid) {
        log.info("密码支付，风控校验安全");
        return true;
    }


}
