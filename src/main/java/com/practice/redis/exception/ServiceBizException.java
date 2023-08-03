package com.practice.redis.exception;

public class ServiceBizException extends RuntimeException {
    private static final long serialVersionUID = 7895884193269203187L;

    public ServiceBizException() {
        super();
    }

    public ServiceBizException(String message) {
        super(message);
    }

    public ServiceBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceBizException(Throwable cause) {
        super(cause);
    }
}