package com.practice.design.factory.service;

import com.practice.design.factory.PayOrderMapper;
import com.practice.design.factory.config.PayProperties;
import com.practice.design.factory.entity.*;
import com.practice.design.factory.pay.PayClient;
import com.practice.design.factory.pay.PayClientFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 支付订单实现
 */
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayChannelService channelService;

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayOrderMapper orderMapper;

    @Autowired
    private PayProperties payProperties;

    @Override
    public PayOrderUnifiedRespDTO submitPayOrder(PayOrderSubmitReqVO reqVO) {
        PayOrderDO order = orderMapper.selectById(reqVO.getId());
        PayChannelDO channel = channelService.validPayChannel(order.getAppId(), reqVO.getChannelCode());
        //根据渠道拿到支付客户端，调用下单接口，进行下单
        PayClient client = payClientFactory.getPayClient(channel.getId());
        //进行数据组装和check，完成后，调用统一接口进行订单提交
        PayOrderUnifiedReqDTO unifiedOrderReqDTO = new PayOrderUnifiedReqDTO();
                // 商户相关的字段
        unifiedOrderReqDTO.setMerchantOrderId("66666") // 注意，此处使用的是 PayOrderExtensionDO.no 属性！
                .setSubject(order.getSubject()).setBody(order.getBody())
                .setNotifyUrl(genChannelPayNotifyUrl(channel))
                .setReturnUrl(genChannelReturnUrl(channel))
                // 订单相关字段
                .setAmount(order.getAmount()).setExpireTime(order.getExpireTime());
        PayOrderUnifiedRespDTO unifiedOrderRespDTO = client.unifiedOrder(unifiedOrderReqDTO);
        return unifiedOrderRespDTO;
    }

    /**
     * 根据支付渠道的编码，生成支付渠道的回调地址
     *
     * @param channel 支付渠道
     * @return 支付渠道的回调地址  配置地址 + "/" + channel id
     */
    private String genChannelPayNotifyUrl(PayChannelDO channel) {
        return payProperties.getCallbackUrl() + "/" + channel.getId();
    }

    /**
     * 根据支付渠道的编码，生成支付渠道的返回地址
     * @param channel 支付渠道
     * @return 支付成功返回的地址。 配置地址 + "/" + channel id
     */
    private String genChannelReturnUrl(PayChannelDO channel) {
        return payProperties.getReturnUrl() + "/" + channel.getId();
    }

}
