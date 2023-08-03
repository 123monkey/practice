package com.practice.design.decorator.v2;

public interface State {
    // 默认实现，不做任何处理
    default void update(Task task, ActionType actionType) {
        // do nothing
    }
}