package com.practice.design.factory.pay;

import com.practice.design.factory.config.AlipayPayClientConfig;

public  abstract class AbstractAlipayClient extends AbstractPayClient<AlipayPayClientConfig> {
    public AbstractAlipayClient(Long channelId, String code, AlipayPayClientConfig config) {
        super();
    }
}
