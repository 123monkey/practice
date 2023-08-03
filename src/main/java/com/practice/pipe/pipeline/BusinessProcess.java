package com.practice.pipe.pipeline;

/**
 * 业务执行器
 * @param <T>
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    void process(ProcessContext<T> context);
}
