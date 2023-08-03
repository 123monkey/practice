package com.practice.design.factory.pay;

import com.alibaba.fastjson.JSON;
import com.practice.design.factory.config.PayClientConfig;
import com.practice.design.factory.entity.PayOrderUnifiedReqDTO;
import com.practice.design.factory.entity.PayOrderUnifiedRespDTO;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Validation;

@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {

    @Override
    public final PayOrderUnifiedRespDTO unifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        Validation.buildDefaultValidatorFactory().getValidator().validate(reqDTO);
        // 执行短信发送
        PayOrderUnifiedRespDTO result = null;
        try {
            result = doUnifiedOrder(reqDTO);
        } catch (Throwable ex) {
            // 打印异常日志
            log.error("[unifiedOrder][request({}) 发起支付失败]", JSON.toJSONString(result),JSON.toJSONString(reqDTO));
        }
        return result;
    }

    protected abstract PayOrderUnifiedRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO)
            throws Throwable;

}
