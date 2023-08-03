package com.practice.design.state.observer;

import com.practice.design.state.v1.ActionType;
import com.practice.design.state.v2.State;
import com.practice.design.state.v2.Task;

// 任务初始状态
class TaskInit implements State {
    @Override
    public void update(Task task, ActionType actionType) {
        if  (actionType == ActionType.START) {
            TaskOngoing taskOngoing = new TaskOngoing();
            taskOngoing.add(new ActivityObserver());
            taskOngoing.add(new TaskManageObserver());
            task.setState(taskOngoing);
        }
    }
}