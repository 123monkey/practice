package com.practice.objectpool.template;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.remoting.RPCHook;

import java.lang.reflect.Constructor;

/**
 * @author liuyazhou
 */
public class SohuMQAdminFactory {
    private MQCloudConfigHelper mqCloudConfigHelper;
    private Class<SohuMQAdmin> sohuMQAdminClass;

    /**
     * admin工厂中包含了配置助手和mqAdmin的class
     * @param mqCloudConfigHelper
     * @param sohuMQAdminClass
     */
    public SohuMQAdminFactory(MQCloudConfigHelper mqCloudConfigHelper, Class<SohuMQAdmin> sohuMQAdminClass) {
        this.mqCloudConfigHelper = mqCloudConfigHelper;
        this.sohuMQAdminClass = sohuMQAdminClass;
    }

    /**
     * 获取启动好的实例
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public SohuMQAdmin getInstance(Cluster key) throws Exception {
        System.setProperty("rocketmq.namesrv.domain", mqCloudConfigHelper.getDomain());
        SohuMQAdmin sohuMQAdmin = null;
        if (mqCloudConfigHelper.isAdminAclEnable()) {
            SessionCredentials credentials = new SessionCredentials(mqCloudConfigHelper.getAdminAccessKey(),
                    mqCloudConfigHelper.getAdminSecretKey());
            Constructor<SohuMQAdmin> constructor = sohuMQAdminClass.getConstructor(RPCHook.class, long.class);
            sohuMQAdmin = constructor.newInstance(new AclClientRPCHook(credentials), 5000);
        } else {
            sohuMQAdmin = sohuMQAdminClass.newInstance();
        }
        sohuMQAdmin.setVipChannelEnabled(false);
        sohuMQAdmin.setUnitName(String.valueOf(key.getId()));
        sohuMQAdmin.start();
        return sohuMQAdmin;
    }
}
