package com.practice.design.factory.entity;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class PayOrderSubmitReqVO {


    @NotNull(message = "支付单编号不能为空")
    private Long id;

    @NotEmpty(message = "支付渠道不能为空")
    private String channelCode;


    private Map<String, String> channelExtras;

   // 参见 {@link PayDisplayModeEnum} 枚举。如果不传递，则每个支付渠道使用默认的方式
    private String displayMode;
}