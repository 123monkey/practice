package com.practice.design.reward.v2;

// 优化后的策略服务
class RewardService {
    public void issueReward(String rewardType, Object ... params) {
        Strategy strategy = StrategyContext.getStrategy(rewardType);
        strategy.issue(params);
    }
}