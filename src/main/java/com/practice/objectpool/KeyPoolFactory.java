package com.practice.objectpool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * key池工厂
 */
public class KeyPoolFactory {
    /**
     * 对象池： key和对应的bean对象
     */
    private static GenericKeyedObjectPool<String, MyBean> pool;
    /**
     * 对象池的参数设置 key-object池配置
     */
    private static final GenericKeyedObjectPoolConfig config;

    /**
     * 对象池每个key最大实例化对象数
     */
    private final static int TOTAL_PERKEY = 10;
    /**
     * 对象池每个key最大的闲置对象数
     */
    private final static int IDLE_PERKEY = 3;
    static {
        config = new GenericKeyedObjectPoolConfig();
        // 总最大perkey=>10,闲置最大3个
        config.setMaxTotalPerKey(TOTAL_PERKEY);
        config.setMaxIdlePerKey(IDLE_PERKEY);
        /** 支持jmx管理扩展 */
        config.setJmxEnabled(true);
        config.setJmxNamePrefix("myPoolProtocol");
        /** 保证获取有效的池对象 */
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
    }

    /**
     * 从对象池中获取对象
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static MyBean getBean(String key) throws Exception {
        if (pool == null) {
            init();
        }
        return pool.borrowObject(key);
    }

    /**
     * 归还对象
     * 
     * @param key
     * @param bean
     */
    public static void returnBean(String key, MyBean bean) {
        if (pool == null) {
            init();
        }
        pool.returnObject(key, bean);
    }

    /**
     * 关闭对象池
     */
    public synchronized static void close() {
        if (pool != null && !pool.isClosed()) {
            pool.close();
            pool = null;
        }
    }

    /**
     * 初始化对象池
     */
    private synchronized static void init() {
        if (pool != null) {
            return;
        }
        pool = new GenericKeyedObjectPool<String, MyBean>(new MyBeanPooledFactory(), config);
    }

    /**
     * 对象工厂
     */
    static class MyBeanPooledFactory extends BaseKeyedPooledObjectFactory<String, MyBean> {
        /**
         * 创建对象
         * 
         * @param key
         * @return
         * @throws Exception
         */
        @Override
        public MyBean create(String key) throws Exception {
            MyBean myBean = new MyBean();
            myBean.start();
            System.out.println(myBean);
            return myBean;
        }

        @Override
        public PooledObject<MyBean> wrap(MyBean value) {
            return new DefaultPooledObject<MyBean>(value);
        }

        /**
         * 验证对象是否有效
         * 
         * @param key
         * @param p
         * @return
         */
        @Override
        public boolean validateObject(String key, PooledObject<MyBean> p) {
            MyBean bean = p.getObject();
            if (!bean.isLive()) {
                System.out.println(bean.getName() + "已经死了，无法唤醒他了!");
                return false;
            }
            return true;
        }

        /**
         * 销毁
         * 
         * @param key
         * @param p
         * @throws Exception
         */
        @Override
        public void destroyObject(String key, PooledObject<MyBean> p) throws Exception {
            /** 杀死他 */
            p.getObject().beKilled();
        }

        @Override
        public void activateObject(String key, PooledObject<MyBean> p) throws Exception {
            super.activateObject(key, p);
        }

        @Override
        public void passivateObject(String key, PooledObject<MyBean> p) throws Exception {
            super.passivateObject(key, p);
        }
    }
}