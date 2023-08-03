package com.practice.pipe.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.practice.pipe.SendTaskModel;
import com.practice.pipe.model.MessageParam;
import com.practice.pipe.pipeline.BusinessProcess;
import com.practice.pipe.pipeline.ProcessContext;
import com.practice.pipe.resreq.BasicResultVO;
import com.practice.pipe.resreq.RespStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description 前置参数校验
 */
@Slf4j
@Service
public class PreParamCheckAction implements BusinessProcess<SendTaskModel> {

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();

        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();

        // 1.没有传入 消息模板Id 或者 messageParam
        if (Objects.isNull(messageTemplateId) || CollUtil.isEmpty(messageParamList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }

        // 2.过滤 receiver=null 的messageParam
        List<MessageParam> resultMessageParamList = messageParamList.stream()
                .filter(messageParam -> !StrUtil.isBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(resultMessageParamList)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }

        // 3.过滤receiver大于100的请求
        if (resultMessageParamList.stream().anyMatch(messageParam -> messageParam.getReceiver().split(StrUtil.COMMA).length > 100)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TOO_MANY_RECEIVER));
            return;
        }

        sendTaskModel.setMessageParamList(resultMessageParamList);

    }
}
