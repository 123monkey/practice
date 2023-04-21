package com.practice.objectpool.template;

/**
 * 默认回调
 * @author liuyazhou
 */
public abstract class DefaultCallback<T> implements MQAdminCallback<T> {

    @Override
    public T exception(Exception e) {
        return null;
    }

}
