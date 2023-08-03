package com.practice.design.factory.entity;

import java.io.Serializable;

/**
 * 返回结果包装
 */
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     *
     */
    private String msg;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 200;
        result.data = data;
        result.msg = "";
        return result;
    }

}
