package com.practice.pipe.model;

import lombok.Data;

import java.util.Map;

/**
 * 消息参数
 */
@Data
public class MessageParam {

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 相关填充参数
     */
    private Map<String,String> variables;
    /**
     * 扩展参数
     */
    private Map<String,String> extra;
}
