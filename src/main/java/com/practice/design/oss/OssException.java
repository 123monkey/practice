package com.practice.design.oss;

public class OssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
