package com.practice.pipe.service;

import com.practice.pipe.resreq.SendRequest;
import com.practice.pipe.resreq.SendResponse;

/**
 * 发送消息
 */
public interface SendService {
    /**
     * 发送消息
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);
}
