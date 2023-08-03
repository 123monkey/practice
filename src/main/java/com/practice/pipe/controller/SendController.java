package com.practice.pipe.controller;

import com.practice.pipe.resreq.SendRequest;
import com.practice.pipe.resreq.SendResponse;
import com.practice.pipe.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 发送消息
 */
public class SendController {

    @Autowired
    private SendService sendService;


    /**
     * 单个文案下发相同的人
     *
     * @param sendRequest
     * @return
     */
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }
}
