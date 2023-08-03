package com.practice.design.reward.v3;

// 单例酒旅策略
class Hotel extends AbstractStrategy implements Strategy {
   private static final Hotel instance = new Hotel();
   private HotelService hotelService;
    private Hotel() {
        register();
    }
    public static Hotel getInstance() {
        return instance;
    }
    @Override
    public void issue(Object... params) {
        HotelRequest request = new HotelRequest();
        request.addHotelReq(params);
        hotelService.sendPrize(request);
    }
}