package com.practice.pipe.receiver;

import cn.hutool.core.collection.CollUtil;
import com.practice.pipe.model.TaskInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable {

    @Autowired
    private HandlerHolder handlerHolder;

    private TaskInfo taskInfo;


    @Override
    public void run() {
        // 3. 发送消息
        if (CollUtil.isNotEmpty(taskInfo.getReceiver())) {
            handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
        }

    }
}
