package com.practice.design.reward.v3;

// 抽象策略类
abstract class AbstractStrategy implements Strategy {
    // 类注册方法
    public void register() {
        StrategyContext.registerStrategy(getClass().getSimpleName(), this);
    }
}