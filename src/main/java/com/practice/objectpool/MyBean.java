package com.practice.objectpool;

import java.sql.Timestamp;

/**
 * bean对象
 */
public class MyBean {

    public static final String[] names = { "Littlehow", "Jim Green", "Black Tom", "White Cat", "Yellow Dog",
            "Color Wolf" };
    /**
     * bean被初始化的时间
     */
    private long instanceTime = System.currentTimeMillis();

    /**
     * 名称
     */
    private String name;

    /**
     * 对象是否存活
     */
    private boolean live = true;

    /**
     * 实例化对象
     */
    public MyBean() {
        this.name = names[(int) (this.instanceTime % names.length)];
    }

    /**
     * name由调用方指定
     * 
     * @param name
     */
    public MyBean(String name) {
        this.name = name;
    }

    /**
     * 销毁方法
     */
    public void beKilled() {
        System.out.print("我[" + this.name + "]居然被销毁了，我不甘心啊,");
        System.out.println("就活了[" + (System.currentTimeMillis() - this.instanceTime) + "]毫秒!");
    }

    @Override
    public String toString() {
        return "我[" + this.name + "]出生在:" + new Timestamp(this.instanceTime);
    }

    /**
     * 获取实例化时间
     * 
     * @return
     */
    public long getInstanceTime() {
        return this.instanceTime;
    }

    /**
     * 获取对象标志
     * 
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 死亡掉
     */
    public void deadBean() {
        this.live = false;
    }

    /**
     * 人不是还活着
     * 
     * @return
     */
    public boolean isLive() {
        return this.live;
    }

    /**
     * 相当于打开一个链接对象，如果是管理数据库连接的话
     */
    public void start() {
        System.out.println(this.name + "的生命开始了");
    }
}