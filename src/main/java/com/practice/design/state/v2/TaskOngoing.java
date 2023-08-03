package com.practice.design.state.v2;

// 任务进行状态
public class TaskOngoing implements State {
    private ActivityService activityService;
    private TaskManager taskManager;

    public void update(Task task, ActionType actionType) {
        if (actionType == ActionType.ACHIEVE) {
            task.setState(new TaskFinished());
            // 通知

            activityService.notifyFinished(task.getTaskId());
            taskManager.release(task.getTaskId());
        } else if (actionType == ActionType.STOP) {
            task.setState(new TaskPaused());
        } else if (actionType == ActionType.EXPIRE) {
            task.setState(new TaskExpired());
        }
    }
}