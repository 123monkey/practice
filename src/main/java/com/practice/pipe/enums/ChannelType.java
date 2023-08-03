package com.practice.pipe.enums;


import com.practice.pipe.model.ContentModel;
import com.practice.pipe.model.ImContentModel;
import com.practice.pipe.model.PushContentModel;
import com.practice.pipe.model.SmsContentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

/**
 * 发送渠道类型枚举
 *
 * @author 3y
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelType implements PowerfulEnum{


    /**
     * IM(站内信)  -- 未实现该渠道
     */
    IM(10, "IM(站内信)", ImContentModel.class, "im"),
    /**
     * push(通知栏) --安卓 已接入 个推
     */
    PUSH(20, "push(通知栏)", PushContentModel.class, "push"),
    /**
     * sms(短信)  -- 腾讯云、云片
     */
    SMS(30, "sms(短信)", SmsContentModel.class, "sms"),

    ;

    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 内容模型Class
     */
    private final Class<? extends ContentModel> contentModelClass;

    /**
     * 英文标识
     */
    private final String codeEn;

    /**
     * 通过code获取class
     *
     * @param code
     * @return
     */
    public static Class<? extends ContentModel> getChanelModelClassByCode(Integer code) {
        return Arrays.stream(values()).filter(channelType -> Objects.equals(code, channelType.getCode()))
                .map(ChannelType::getContentModelClass)
                .findFirst().orElse(null);
    }
}
