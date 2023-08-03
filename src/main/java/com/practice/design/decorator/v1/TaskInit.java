package com.practice.design.decorator.v1;

import com.practice.design.state.v2.TaskOngoing;

// 任务初始状态
public class TaskInit implements State {

    @Override
    public void update(Task task, ActionType actionType) {
        if  (actionType == ActionType.START) {
            task.setState(new TaskOngoing());
        }
    }
}