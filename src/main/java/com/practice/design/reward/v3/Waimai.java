package com.practice.design.reward.v3;

// 单例外卖策略
class Waimai extends AbstractStrategy implements Strategy {
    private static final Waimai instance = new Waimai();
   private WaimaiService waimaiService;
    private Waimai() {
        register();
    }
    public static Waimai getInstance() {
        return instance;
    }
    @Override
    public void issue(Object... params) {
        WaimaiRequest request = new WaimaiRequest();
        // 构建入参
        request.setWaimaiReq(params);
        waimaiService.issueWaimai(request);
    }
}