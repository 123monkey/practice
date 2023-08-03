package com.practice.design.reward.v2;

// 酒旅策略
class Hotel implements Strategy {
   private HotelService hotelService;
    @Override
    public void issue(Object... params) {
        HotelRequest request = new HotelRequest();
        request.addHotelReq(params);
        hotelService.sendPrize(request);
    }
}