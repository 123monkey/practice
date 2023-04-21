package com.practice.objectpool.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.tools.admin.MQAdminExt;

/**
 * MQAdmin对象池
 * 
 * @Description:
 * @author yongfeigao
 * @date 2018年7月3日
 */
@Slf4j
public class MQAdminPooledObjectFactory implements KeyedPooledObjectFactory<Cluster, MQAdminExt> {

    private SohuMQAdminFactory sohuMQAdminFactory;

    //获取mqAdminExt对象 实列
    @Override
    public PooledObject<MQAdminExt> makeObject(Cluster key) throws Exception {
        DefaultPooledObject<MQAdminExt> pooledObject = new DefaultPooledObject<MQAdminExt>(
                sohuMQAdminFactory.getInstance(key));
        log.info("create object, key:{}", key);
        return pooledObject;
    }

    //销毁对象 mqAdmin
    @Override
    public void destroyObject(Cluster key, PooledObject<MQAdminExt> p) throws Exception {
        MQAdminExt mqAdmin = p.getObject();
        if (mqAdmin != null) {
            try {
                mqAdmin.shutdown();
            } catch (Exception e) {
                log.warn("shutdown err, key:{}", key, e);
            }
        }
        log.info("destroy object {}", key);
    }

    //校验对象 mqAdmin
    @Override
    public boolean validateObject(Cluster key, PooledObject<MQAdminExt> p) {
        MQAdminExt mqAdmin = p.getObject();
        ClusterInfo clusterInfo = null;
        try {
            clusterInfo = mqAdmin.examineBrokerClusterInfo();
        } catch (Exception e) {
            log.warn("validate object err, key:{}", key, e);
        }
        if (clusterInfo == null) {
            return false;
        }
        if (clusterInfo.getBrokerAddrTable() == null) {
            return false;
        }
        if (clusterInfo.getBrokerAddrTable().size() <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void activateObject(Cluster key, PooledObject<MQAdminExt> p) throws Exception {

    }

    @Override
    public void passivateObject(Cluster key, PooledObject<MQAdminExt> p) throws Exception {
    }

    public void setSohuMQAdminFactory(SohuMQAdminFactory sohuMQAdminFactory) {
        this.sohuMQAdminFactory = sohuMQAdminFactory;
    }
}
