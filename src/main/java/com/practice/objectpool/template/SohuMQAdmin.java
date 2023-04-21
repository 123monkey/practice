package com.practice.objectpool.template;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExtImpl;

import java.lang.reflect.Field;

/**
 * sohu实现，为了添加扩展某些方法
 * 
 * @author yongfeigao
 * @date 2018年10月16日
 */
public abstract class SohuMQAdmin extends DefaultMQAdminExt {

    public SohuMQAdmin() {
        super();
    }

    //rocketmq的默认方法
    public SohuMQAdmin(RPCHook rpcHook, long timeoutMillis) {
        super(rpcHook, timeoutMillis);
    }

    /**
     * 获取系统内置topic
     * 
     * @param timeoutMillis
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws RemotingException
     * @throws MQClientException
     * @throws InterruptedException
     */
    public TopicList getSystemTopicList(long timeoutMillis)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
            RemotingException, MQClientException, InterruptedException {
        //获取mq客户端实列,进一步获取mq客户端api时许，然后拿到系统主题列表
        return getMQClientInstance().getMQClientAPIImpl().getSystemTopicList(timeoutMillis);
    }

    /**
     * 发送消息
     * 
     * @param msg
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws MQClientException
     * @throws RemotingException
     * @throws MQBrokerException
     * @throws InterruptedException
     */
    public SendResult sendMessage(Message msg) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException, MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return getMQClientInstance().getDefaultMQProducer().send(msg);
    }

    /**
     * 获取 @MQClientInstance
     * 
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public MQClientInstance getMQClientInstance()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // 反射获取defaultMQAdminExtImpl实例
        Field defaultMQAdminExtImplField = DefaultMQAdminExt.class.getDeclaredField("defaultMQAdminExtImpl");
        defaultMQAdminExtImplField.setAccessible(true);
        DefaultMQAdminExtImpl defaultMQAdminExtImpl = (DefaultMQAdminExtImpl) defaultMQAdminExtImplField.get(this);
        // 反射获取mqClientInstance实例
        Field field = DefaultMQAdminExtImpl.class.getDeclaredField("mqClientInstance");
        field.setAccessible(true);
        MQClientInstance mqClientInstance = (MQClientInstance) field.get(defaultMQAdminExtImpl);
        return mqClientInstance;
    }
}
