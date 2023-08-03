package com.practice.review.factory.v2;

/**
 * 随项目启动进行初始化
 */
public class StoreNewFactory extends StoreConfig{

    public ICommodity getCommodityService(Integer type){
        return storeMap.get(type);
    }


}
