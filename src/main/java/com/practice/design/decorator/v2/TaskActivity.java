package com.practice.design.decorator.v2;

// 任务型活动
class TaskActivity extends Activity {
    protected Task task;
   // 全参构造函数
    public TaskActivity(String type, Long id, String name, Integer scene, String material, Task task) {
        super(type, id, name, scene, material);
        this.task = task;
    }
   // 参与任务型活动
    @Override
    public void participate(Long userId) {
        // 更新任务状态为进行中
        task.getState().update(task, ActionType.START);
    }
    // 继承建造器类
    public static class Builder extends Activity.Builder<Builder> {
        private Task task;
        public Builder setTask(Task task) {
            this.task = task;
            return this;
        }
        @Override
        public TaskActivity build(){
            return new TaskActivity(type, id, name, scene, material, task);
        }
    }
}