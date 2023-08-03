package com.practice.design.state.v2;

// 任务初始状态
public class TaskInit implements State {

    public void update(Task task, ActionType actionType) {
        if  (actionType == ActionType.START) {
            task.setState(new TaskOngoing());
        }
    }
}