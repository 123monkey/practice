package com.practice.pipe.resreq;

import com.practice.pipe.model.MessageParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SendRequest {

    /**
     * 执行业务类型
     * send:发送消息
     * recall:撤回消息
     */
    private String code;

    /**
     * 消息模板Id
     * 【必填】
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     * 当业务类型为"send"，必传
     */
    private MessageParam messageParam;


}