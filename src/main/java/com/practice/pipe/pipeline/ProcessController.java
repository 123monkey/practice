package com.practice.pipe.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.practice.pipe.exception.ProcessException;
import com.practice.pipe.resreq.BasicResultVO;
import com.practice.pipe.resreq.RespStatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 灵魂类 - 处理器控制器
 */
@Data
@Slf4j
public class ProcessController {

    /**
     * 模板映射
     */
    private Map<String,ProcessTemplate> templateConfig = null;


    /**
     * 处理器处理方法
     */
    public ProcessContext process(ProcessContext context) {
        //前置处理
        try{
           preCheck(context);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //遍历流程节点进行处理
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess: processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context;
    }

    /**
     * 执行前检查，出错则抛出异常
     *
     * @param context 执行上下文
     * @throws ProcessException 异常信息
     */
    private void preCheck(ProcessContext context) throws ProcessException {
        // 上下文
        if (Objects.isNull(context)) {
            context = new ProcessContext();
            context.setResponse(BasicResultVO.fail(RespStatusEnum.CONTEXT_IS_NULL));
            throw new ProcessException(context);
        }

        // 业务代码
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.BUSINESS_CODE_IS_NULL));
            throw new ProcessException(context);
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(processTemplate)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_TEMPLATE_IS_NULL));
            throw new ProcessException(context);
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_LIST_IS_NULL));
            throw new ProcessException(context);
        }

    }


}
