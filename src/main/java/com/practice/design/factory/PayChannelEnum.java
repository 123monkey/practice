package com.practice.design.factory;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付渠道
 */
/*
@Getter
@AllArgsConstructor
public enum PayChannelEnum {

    WX_PUB("wx_pub", "微信 JSAPI 支付", WXPayClientConfig.class), // 公众号网页
    WX_LITE("wx_lite", "微信小程序支付", WXPayClientConfig.class),
    WX_APP("wx_app", "微信 App 支付", WXPayClientConfig.class),
    WX_NATIVE("wx_native", "微信 native 支付", WXPayClientConfig.class),

    ALIPAY_PC("alipay_pc", "支付宝 PC 网站支付", AlipayPayClientConfig.class),
    ALIPAY_WAP("alipay_wap", "支付宝 Wap 网站支付", AlipayPayClientConfig.class),
    ALIPAY_APP("alipay_app", "支付宝App 支付", AlipayPayClientConfig.class),
    ALIPAY_QR("alipay_qr", "支付宝扫码支付", AlipayPayClientConfig.class),
    ALIPAY_BAR("alipay_bar", "支付宝条码支付", AlipayPayClientConfig.class);


    private final  String code;

    private final String name;

    private final Class<? extends PayClientConfing> configClass;

     */
/**
      * 微信支付
     *//*

    public static final String WECHAT = "WECHAT";

    */
/**
     * 支付宝支付
     *//*

    public static final String ALIPAY = "ALIPAY";

    public static  PayChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o->o.getCode().equals(code), values());
    }

}
*/
