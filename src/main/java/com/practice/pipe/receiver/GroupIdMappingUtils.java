package com.practice.pipe.receiver;

import com.practice.pipe.enums.ChannelType;
import com.practice.pipe.enums.EnumUtil;
import com.practice.pipe.enums.MessageType;
import com.practice.pipe.model.TaskInfo;

import java.util.ArrayList;
import java.util.List;

public class GroupIdMappingUtils {

    /**
     * 获取所有的groupIds
     * (不同的渠道不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }


    /**
     * 根据TaskInfo获取当前消息的groupId
     *
     * @param taskInfo
     * @return
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = EnumUtil.getEnumByCode(taskInfo.getSendChannel(), ChannelType.class).getCodeEn();
        String msgCodeEn = EnumUtil.getEnumByCode(taskInfo.getMsgType(), MessageType.class).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}
