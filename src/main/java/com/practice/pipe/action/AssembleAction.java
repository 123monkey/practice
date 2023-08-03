package com.practice.pipe.action;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.practice.pipe.SendTaskModel;
import com.practice.pipe.dao.MessageTemplateDao;
import com.practice.pipe.enums.ChannelType;
import com.practice.pipe.model.ContentModel;
import com.practice.pipe.model.MessageParam;
import com.practice.pipe.model.MessageTemplate;
import com.practice.pipe.model.TaskInfo;
import com.practice.pipe.pipeline.BusinessProcess;
import com.practice.pipe.pipeline.ProcessContext;
import com.practice.pipe.resreq.BasicResultVO;
import com.practice.pipe.resreq.RespStatusEnum;
import com.practice.pipe.utils.ContentHolderUtil;
import com.practice.pipe.utils.TaskInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class AssembleAction  implements BusinessProcess<SendTaskModel> {
    private static final String LINK_NAME = "url";

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();

        try {
            Optional<MessageTemplate> messageTemplate = messageTemplateDao.findById(messageTemplateId);
            if (!messageTemplate.isPresent() || messageTemplate.get().getIsDeleted().equals(1)) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
                return;
            }
            if ("commonSend".equals(context.getCode())) {
                List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel, messageTemplate.get());
                sendTaskModel.setTaskInfo(taskInfos);
            } else if ("recall".equals(context.getCode())) {
                sendTaskModel.setMessageTemplate(messageTemplate.get());
            }
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("assemble task fail! templateId:{}, e:{}", messageTemplateId, e.getMessage());
        }

    }

    /**
     * 组装 TaskInfo 任务消息
     *
     * @param sendTaskModel
     * @param messageTemplate
     */
    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {

            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), messageTemplate.getTemplateType()))
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrUtil.C_COMMA)))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .shieldType(messageTemplate.getShieldType())
                    .sendAccount(messageTemplate.getSendAccount())
                    .contentModel(getContentModelValue(messageTemplate, messageParam)).build();

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;

    }


    /**
     * 获取 contentModel，替换模板msgContent中占位符信息
     */
    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {

        // 得到真正的ContentModel 类型
        Integer sendChannel = messageTemplate.getSendChannel();
        Class<? extends ContentModel> contentModelClass = ChannelType.getChanelModelClassByCode(sendChannel);

        // 得到模板的 msgContent 和 入参
        Map<String, String> variables = messageParam.getVariables();
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());


        // 通过反射 组装出 contentModel
        Field[] fields = ReflectUtil.getFields(contentModelClass);
        ContentModel contentModel = ReflectUtil.newInstance(contentModelClass);
        for (Field field : fields) {
            String originValue = jsonObject.getString(field.getName());

            if (StrUtil.isNotBlank(originValue)) {
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                Object resultObj = JSONUtil.isJsonObj(resultValue) ? JSONUtil.toBean(resultValue, field.getType()) : resultValue;
                ReflectUtil.setFieldValue(contentModel, field, resultObj);
            }
        }

        // 如果 url 字段存在，则在url拼接对应的埋点参数
        String url = (String) ReflectUtil.getFieldValue(contentModel, LINK_NAME);
        if (StrUtil.isNotBlank(url)) {
            String resultUrl = TaskInfoUtils.generateUrl(url, messageTemplate.getId(), messageTemplate.getTemplateType());
            ReflectUtil.setFieldValue(contentModel, LINK_NAME, resultUrl);
        }
        return contentModel;
    }
}
