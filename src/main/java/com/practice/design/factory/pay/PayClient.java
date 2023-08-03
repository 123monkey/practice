package com.practice.design.factory.pay;

import com.practice.design.factory.entity.PayNotifyReqDTO;
import com.practice.design.factory.entity.PayOrderUnifiedReqDTO;
import com.practice.design.factory.entity.PayOrderUnifiedRespDTO;

public interface PayClient {

    /**
     * 获得渠道编号
     *
     * @return 渠道编号
     */
    Long getId();

    /**
     * 调用支付渠道，统一下单
     *
     * @param reqDTO 下单信息
     * @return 各支付渠道的返回结果
     */
    PayOrderUnifiedRespDTO unifiedOrder(PayOrderUnifiedReqDTO reqDTO);

    /**
     * 解析回调数据
     *
     * @param rawNotify 通知内容
     * @return 回调对象
     *         1. {@link PayRefundNotifyRespDTO} 退款通知
     *         2. {@link PayOrderNotifyRespDTO} 支付通知
     */
    default Object parseNotify(PayNotifyReqDTO rawNotify) {
        throw new UnsupportedOperationException("未实现 parseNotify 方法！");
    }

}