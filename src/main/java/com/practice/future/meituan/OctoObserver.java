package com.practice.future.meituan;

/**
 * @author tech
 */
public interface OctoObserver<T> {
    void onSuccess(T t);

    void onFailure(Throwable throwable);
}