package com.practice.design.state.v2;

import com.practice.design.state.v1.ActionType;
import com.practice.design.state.v1.Task;

// 任务暂停状态
public class TaskPaused implements State {

    public void update(Task task, ActionType actionType) {
        if (actionType == ActionType.START) {
            task.setState(new TaskOngoing());
        } else if (actionType == ActionType.EXPIRE) {
            task.setState(new TaskExpired());
        }
    }
}