package com.practice.objectpool.template;

public enum Status {

    // 2xx代表正常返回
    OK(200, "OK"),
    DB_ERROR(500, "数据库异常"),
    NO_RESULT(201, "暂无数据"),

    ;

    private int key;
    private String value;

    private Status(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
