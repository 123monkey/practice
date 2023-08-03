package com.practice.design.state.observer;

import com.practice.design.state.v2.TaskManager;

// 任务管理观察者
class TaskManageObserver implements Observer {
    private TaskManager taskManager;
    @Override
    public void response(Long taskId) {
        taskManager.release(taskId);
    }
}