package com.practice.objectpool.template;

import org.springframework.stereotype.Service;

@Service
public class ClusterService {

    // cluster持有，初始化后即缓存到内存
    private volatile Cluster[] mqClusterArray;

    /**
     * 根据id查找集群
     *
     * @param id
     * @return
     */
    public Cluster getMQClusterById(long id) {
        for (Cluster mqCluster : getAllMQCluster()) {
            if (id == mqCluster.getId()) {
                return mqCluster;
            }
        }
        return null;
    }

    //基于volatile可进行拿到集群的数据信息
    public Cluster[] getAllMQCluster() {
        return mqClusterArray;
    }

}
