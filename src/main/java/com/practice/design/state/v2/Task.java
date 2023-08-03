package com.practice.design.state.v2;

import com.practice.design.state.v1.ActionType;
import lombok.Data;

@Data
public class Task {
    private Long taskId;
    // 初始化为初始态
    private State state = new TaskInit();
    // 更新状态
    public void updateState(ActionType actionType) {
        state.update(this, actionType);
    }
}