package com.practice.pipe.config;

import com.practice.pipe.action.AfterParamCheckAction;
import com.practice.pipe.action.AssembleAction;
import com.practice.pipe.action.PreParamCheckAction;
import com.practice.pipe.action.SendMqAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 注入对应的action和对应的pipeline
 */
@Configuration
public class PipelineConfig {

    @Autowired
    private PreParamCheckAction preParamCheckAction;
    @Autowired
    private AssembleAction assembleAction;
    @Autowired
    private AfterParamCheckAction afterParamCheckAction;
    @Autowired
    private SendMqAction sendMqAction;


}
