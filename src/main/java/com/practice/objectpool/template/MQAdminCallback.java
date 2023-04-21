package com.practice.objectpool.template;

import org.apache.rocketmq.tools.admin.MQAdminExt;

/**
 * MQAdmin回调: 具体操作回调、异常回调、mq集群
 * @param <T>
 */
public interface MQAdminCallback<T> {
    
    /**
     * 具体操作回调
     * @param mqAdmin
     * @return
     * @throws Exception
     */
	T callback(MQAdminExt mqAdmin) throws Exception;
	
	/**
	 * 发生异常时回调
	 * @param e
	 * @return
	 * @throws Exception
	 */
	T exception(Exception e) throws Exception;
	
	/**
	 * 需要指定操作的集群
	 * @return MQCluster
	 */
	Cluster mqCluster();
}
