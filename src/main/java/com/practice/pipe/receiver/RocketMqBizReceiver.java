package com.practice.pipe.receiver;

import com.alibaba.fastjson.JSON;
import com.practice.pipe.model.TaskInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue ="rocketmq")
@RocketMQMessageListener(topic = "${austin.business.topic.name}",
        consumerGroup = "${austin.rocketmq.biz.consumer.group}",
        selectorType = SelectorType.TAG,
        selectorExpression = "${austin.business.tagId.value}"
)
public class RocketMqBizReceiver implements RocketMQListener<String> {

    @Autowired
    private ConsumeService consumeService;

    @Override
    public void onMessage(String message) {
        if (StringUtils.isBlank(message)) {
            return;
        }
        List<TaskInfo> taskInfoLists = JSON.parseArray(message, TaskInfo.class);
        consumeService.consume2Send(taskInfoLists);
    }
}
