package com.practice.pipe.receiver;

import com.practice.pipe.model.TaskInfo;

public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

}