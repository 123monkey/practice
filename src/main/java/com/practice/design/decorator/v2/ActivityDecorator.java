package com.practice.design.decorator.v2;

// 抽象装饰角色
abstract class ActivityDecorator implements ActivityInterface {
    protected ActivityInterface activity;
    public ActivityDecorator(ActivityInterface activity) {
        this.activity = activity;
    }
    public abstract void participate(Long userId);
}