package com.practice.pipe.service;

import com.practice.pipe.SendTaskModel;
import com.practice.pipe.pipeline.ProcessContext;
import com.practice.pipe.pipeline.ProcessController;
import com.practice.pipe.resreq.BasicResultVO;
import com.practice.pipe.resreq.RespStatusEnum;
import com.practice.pipe.resreq.SendRequest;
import com.practice.pipe.resreq.SendResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * 粘合责任链模式
 */
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {
        if(ObjectUtils.isEmpty(sendRequest)){
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg());
        }

        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }

}
