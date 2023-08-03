package com.practice.pipe.receiver;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolUtils {

   // @Autowired
  //  private ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    private static final String SOURCE_NAME = "austin";


    /**
     * 1. 将当前线程池 加入到 动态线程池内
     * 2. 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);
      //  shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
