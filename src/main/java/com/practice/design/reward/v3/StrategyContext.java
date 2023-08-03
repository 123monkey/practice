package com.practice.design.reward.v3;

import java.util.HashMap;
import java.util.Map;

// 策略上下文，用于管理策略的注册和获取
class StrategyContext {
    private static final Map<String, Strategy> registerMap = new HashMap<>();
    // 注册策略
    public static void registerStrategy(String rewardType, Strategy strategy) {
        registerMap.putIfAbsent(rewardType, strategy);
    }
    // 获取策略
    public static Strategy getStrategy(String rewardType) {
        return registerMap.get(rewardType);
    }
}