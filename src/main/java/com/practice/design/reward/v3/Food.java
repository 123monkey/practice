package com.practice.design.reward.v3;

// 单例美食策略
class Food extends AbstractStrategy implements Strategy {
   private static final Food instance = new Food();
   private FoodService foodService;
    private Food() {
        register();
    }
    public static Food getInstance() {
        return instance;
    }
    @Override
    public void issue(Object... params) {
       FoodRequest request = new FoodRequest(params);
        foodService.payCoupon(request);
    }
}