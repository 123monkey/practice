package com.practice.objectpool;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 获取bean信息
 */
public class GetBean {
    static String[] keys = {"2016052700", "2016052800", "2016052900"};
    /** 随机key */
    Random r = new Random();

    /**
     * 获取key值
     * @return
     */
    String getKey () {
        return keys[r.nextInt(keys.length)];
    }
    /**
     * 获取对象
     */
    @Test
    public void getBean () {
        String key = getKey();
        /** 保证key值长度为10 */
        assertTrue( "key的长度必须为10", key != null && key.length() == 10 );
        System.out.println(key);
        try {
            /**
             * 同一个key最多可以获取10个对象
             * 可以看出,当获取10个对象时，对象池就再也不能给出对象了
             */
            for (int i = 0; i < 10; i ++) {
                //睡一秒
                TimeUnit.SECONDS.sleep(1);
                KeyPoolFactory.getBean(key);
            }
            /** 可以看出拿对象时阻塞的 */
            System.out.println("前方是否阻塞到我了...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象再还回去
     */
    @Test
    public void getAndReturnBean () {
        String key = getKey();
        /** 保证key值长度为10 */
        assertTrue( "key的长度必须为10", key != null && key.length() == 10 );
        try {
            /**
             * 同一个key最多可以获取10个对象
             * 可以看出,当获取10个对象时，对象池就再也不能给出对象了
             */
            for (int i = 0; i < 20; i ++) {
                TimeUnit.SECONDS.sleep(1);//睡一秒
                MyBean my = KeyPoolFactory.getBean(key);
                System.out.println(my.getName());
                /** 让对象死掉 */
//                my.deadBean();
                /** 归还对象
                 * 因为执行了my.deadBean(),这时候在归还对象时，他发现对象已经
                 * 不合法了，这时候工厂就会重新拿对象
                 * 如果屏蔽掉my.deadBean()这样的方法,那么获取到的对象将是第一次获取到的对象
                 */
                KeyPoolFactory.returnBean(key, my);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 多个key同时去获取bean
     */
    @Test
    public void getBeans () {
        String key1 = getKey();
        String key2 = key1;
        while (key1.equals(key2)) {
            key2 = getKey();
        }
        assertTrue( "key的长度必须为10,而获取到的key1="+key1, key1 != null && key1.length() == 10 );
        assertTrue( "key的长度必须为10,而获取到的key2="+key2, key2 != null && key2.length() == 10 );
        try {
            /**
             * 同一个key最多可以获取10个对象
             * 20个对象用2个key就可以全部输出
             * 这些都是不归还的
             */
            for (int i = 0; i < 20; i ++) {
                TimeUnit.SECONDS.sleep(1);//睡一秒
                if (i % 2 == 0){
                    KeyPoolFactory.getBean(key1);
                } else {
                    KeyPoolFactory.getBean(key2);
                }
            }
            /**
             * 可以看出这句输出了
             */
            System.out.println("前方是否阻塞到我了...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  一个线程借对象，一个线程还对象
     *  如果获取和归还的时间差特别大，会导致某些时候线程阻塞，因为对象已经拿完了
     *  就好像借书一样，如果书被借完了，那么再想借就得需要等借书人还书了。
     */
    volatile boolean dead = false;//死亡对象标志
    @Test
    public void getAndReturnByThread () {
        /** 对象缓存 */
        final ConcurrentHashMap<String, LinkedBlockingDeque<MyBean>> beans = new ConcurrentHashMap<String, LinkedBlockingDeque<MyBean>>();

        /**
         * 借对象,每秒借一个
         */
        new Thread("borrow") {
            @Override
            public void run(){
                while (true) {
                    String key = getKey();
                    LinkedBlockingDeque<MyBean> link = beans.get(key);
                    if (link == null) {
                        /** 最多存放10个对象 */
                        link = new LinkedBlockingDeque<MyBean>(10);
                        beans.put(key, link);
                    }
                    try {
                        MyBean bean = KeyPoolFactory.getBean(key);
                        link.push(bean);
                        System.out.println(Thread.currentThread().getName() + "操作:key=" + key + ",bean=" + bean);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        /**
         * 还对象,每3秒还一个
         */
        new Thread("return") {
            @Override
            public void run () {
                while (true) {
                    String key = getKey();
                    LinkedBlockingDeque<MyBean> link = beans.get(key);
                    if (link == null || link.size() == 0) {
                        continue;
                    }
                    /** 弹出元素 */
                    MyBean bean = link.pop();
                    if (dead) {
                        bean.deadBean();
                        dead = false;
                    }
                    System.out.println(Thread.currentThread().getName() + "操作:key="+key + ",bean="+bean);
                    KeyPoolFactory.returnBean(key, bean);
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        /**
         * 每5秒死亡一对象
         */
        new Thread("dead") {
            @Override
            public void run () {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        dead = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        try {
            TimeUnit.MINUTES.sleep(5);//5分钟后结束程序
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {

        }
    }
}