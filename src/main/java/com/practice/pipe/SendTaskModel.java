package com.practice.pipe;

import com.practice.pipe.model.MessageParam;
import com.practice.pipe.model.MessageTemplate;
import com.practice.pipe.model.TaskInfo;
import com.practice.pipe.pipeline.ProcessModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 发送task模型
 */
@Data
@Builder
@Accessors(chain = true)
public class SendTaskModel implements ProcessModel {

    /**
     * 消息模板id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的消息
     */
    private List<TaskInfo> taskInfo;

    /**
     * 撤回任务的任务
     */
    private MessageTemplate messageTemplate;
}
