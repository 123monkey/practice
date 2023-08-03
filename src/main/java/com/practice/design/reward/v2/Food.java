package com.practice.design.reward.v2;

// 美食策略
class Food implements Strategy {
   private FoodService foodService;
    @Override
    public void issue(Object... params) {
        FoodRequest request = new FoodRequest(params);
        foodService.payCoupon(request);
    }
}