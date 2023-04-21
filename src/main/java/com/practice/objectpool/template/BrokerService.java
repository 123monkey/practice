package com.practice.objectpool.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

/**
 * 使用mqAdminTemplate案例
 * @author liuyazhou
 */
@Slf4j
public class BrokerService {

    @Autowired
    private MQAdminTemplate mqAdminTemplate;

    @Autowired
    private ClusterService clusterService;

    /**
     * 抓取broker配置
     * @param cid
     * @param brokerAddr
     * @return
     */
    public Result<Properties> fetchBrokerConfig(int cid, String brokerAddr) {
        return mqAdminTemplate.execute(new DefaultCallback<Result<Properties>>() {
            @Override
            public Cluster mqCluster() {
                return clusterService.getMQClusterById(cid);
            }

            @Override
            public Result<Properties> callback(MQAdminExt mqAdmin) throws Exception {
                Properties properties = mqAdmin.getBrokerConfig(brokerAddr);
                return Result.getResult(properties);
            }

            @Override
            public Result<Properties> exception(Exception e) {
                log.error("cid:{} brokerAddr:{}, getBrokerConfig err", cid, brokerAddr, e);
                return Result.getDBErrorResult(e);
            }
        });
    }

}
