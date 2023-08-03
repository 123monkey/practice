package com.practice.pipe.receiver;

import com.practice.pipe.model.TaskInfo;

import java.util.List;

public interface ConsumeService {

    /**
     * 从MQ拉到消息进行消费，发送消息
     *
     * @param taskInfoLists
     */
    void consume2Send(List<TaskInfo> taskInfoLists);

}