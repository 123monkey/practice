package com.practice.design.reward.v2;

// 外卖策略
class Waimai implements Strategy {
   private WaimaiService waimaiService;
    @Override
    public void issue(Object... params) {
        WaimaiRequest request = new WaimaiRequest();
        // 构建入参
        request.setWaimaiReq(params);
        waimaiService.issueWaimai(request);
    }
}