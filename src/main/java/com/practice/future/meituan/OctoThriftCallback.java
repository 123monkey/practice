package com.practice.future.meituan;

/**
 * @author tech
 */
public interface OctoThriftCallback<R, T> {
        void addObserver(OctoObserver<T> octoObserver);
    }