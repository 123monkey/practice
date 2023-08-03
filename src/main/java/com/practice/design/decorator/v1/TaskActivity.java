package com.practice.design.decorator.v1;

// 任务型活动
class TaskActivity extends Activity {
    private Task task;
    public TaskActivity(String type, String name, Integer scene, String material, Task task) {
        super(type, name, scene, material);
        this.task = task;
    }
    // 参与任务型活动
    @Override
    public void participate(Long userId) {
        // 更新任务状态为进行中
        task.getState().update(task, ActionType.START);
    }
}