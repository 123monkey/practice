package com.practice.objectpool.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * mqAdmin模板
 * @author liuyazhou
 */
@Slf4j
public class MQAdminTemplate {

    @Autowired
    private GenericKeyedObjectPool<Cluster, MQAdminExt> mqPool;

    /**
     * 基于池化做一个借和还的模板，同时带回调成功和失败处理
     *
     * @param <T>
     * @return
     */
    public <T> T execute(MQAdminCallback<T> callback) {
        MQAdminExt mqAdmin = null;
        try {
            mqAdmin = mqPool.borrowObject(callback.mqCluster());
            if (mqAdmin == null) {
                //说明不存在可用的mqAdmin
                return null;
            }
            T t = callback.callback(mqAdmin);
            return t;
        } catch (Exception e) {
              //获取mqAdmin对象查询异常
              try{
                 return callback.exception(e);
              }catch (Exception ex){
                  log.warn("cluster:{} exception err:{}", callback.mqCluster(), ex.getMessage());
                 return null;
              }
        } finally {
            if(mqAdmin !=null) {
                try{
                    mqPool.returnObject(callback.mqCluster(), mqAdmin);
                }catch (Exception e) {
                    log.warn("cluster:{} shutdown err:{}", callback.mqCluster(), e.getMessage());
                }

            }
        }
    }

}
