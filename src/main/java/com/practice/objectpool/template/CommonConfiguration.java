package com.practice.objectpool.template;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用配置
 * @author liuyazhou
 */
@Configuration
public class CommonConfiguration {


    @Autowired
    private MQCloudConfigHelper mqCloudConfigHelper;

    /**
     * mq池对象bean创建 池化
     * @param adminClass
     * @return
     */
    @Bean
    public GenericKeyedObjectPool<Cluster, MQAdminExt> mqPool(Class<SohuMQAdmin> adminClass) {
        GenericKeyedObjectPoolConfig genericKeyedObjectPoolConfig = new GenericKeyedObjectPoolConfig();
        genericKeyedObjectPoolConfig.setTestWhileIdle(true);
        genericKeyedObjectPoolConfig.setMaxTotalPerKey(1);
        genericKeyedObjectPoolConfig.setMaxIdlePerKey(1);
        genericKeyedObjectPoolConfig.setMinIdlePerKey(1);
        genericKeyedObjectPoolConfig.setMaxWaitMillis(10000);
        genericKeyedObjectPoolConfig.setTimeBetweenEvictionRunsMillis(20000);
        MQAdminPooledObjectFactory mqAdminPooledObjectFactory = new MQAdminPooledObjectFactory();
        SohuMQAdminFactory sohuMQAdminFactory = new SohuMQAdminFactory(mqCloudConfigHelper, adminClass);
        mqAdminPooledObjectFactory.setSohuMQAdminFactory(sohuMQAdminFactory);
        GenericKeyedObjectPool<Cluster, MQAdminExt> genericKeyedObjectPool = new GenericKeyedObjectPool<Cluster, MQAdminExt>(
                mqAdminPooledObjectFactory,
                genericKeyedObjectPoolConfig);
        return genericKeyedObjectPool;
    }

}
