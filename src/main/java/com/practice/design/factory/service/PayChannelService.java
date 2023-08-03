package com.practice.design.factory.service;

import com.practice.design.factory.entity.PayChannelDO;

public interface PayChannelService {

    /**
     * 支付渠道的合法性
     *
     * 如果不合法，抛出 {@link ServiceException} 业务异常
     *
     * @param appId 应用编号
     * @param code 支付渠道
     * @return 渠道信息
     */
    PayChannelDO validPayChannel(Long appId, String code);
}
