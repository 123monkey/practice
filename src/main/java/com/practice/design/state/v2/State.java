package com.practice.design.state.v2;

import com.practice.design.state.v1.ActionType;

public interface State {
    // 默认实现，不做任何处理
    default void update(Task task, ActionType actionType) {
        // do nothing
    }
}