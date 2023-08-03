package com.practice.design.decorator.v2;

// 能够对活动做风险控制的包装类
class RiskControlDecorator extends ActivityDecorator {
    public RiskControlDecorator(ActivityInterface activity) {
        super(activity);
    }
    @Override
   public void participate(Long userId) {
        // 对目标用户做风险控制，失败则抛出异常
       Risk.doControl(userId);
        // 更新任务状态为进行中
        activity.participate(userId);
    }
}