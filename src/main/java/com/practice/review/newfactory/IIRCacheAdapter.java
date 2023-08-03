package com.practice.review.newfactory;

import java.util.concurrent.TimeUnit;

/**
 * IIR缓存适配
 * @author liuyazhou
 */
public class IIRCacheAdapter  implements ICacheAdapter {

    private IIR iir = new IIR();

    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
         iir.setExprie(key, value,timeout,timeUnit);
    }

    @Override
    public void del(String key) {
       iir.del(key);
    }
}
