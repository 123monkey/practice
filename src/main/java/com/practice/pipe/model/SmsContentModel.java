package com.practice.pipe.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsContentModel extends ContentModel {

    /**
     * 短信发送内容
     */
    private String content;

    /**
     * 短信发送链接
     */
    private String url;

}