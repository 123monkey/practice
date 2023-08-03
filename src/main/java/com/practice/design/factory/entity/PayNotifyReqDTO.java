package com.practice.design.factory.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@Builder
public class PayNotifyReqDTO {


    /**
     * HTTP 回调接口的 request body
     */
    private String body;

    /**
     * HTTP 回调接口 content type 为 application/x-www-form-urlencoded 的所有参数
     */
    private Map<String,String> params;

}