package com.practice.design.oss;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.practice.design.oss.entity.SysOssConfig;
import com.practice.design.oss.entity.TenantEntity;
import com.practice.design.oss.mapper.SysOssConfigMapper;
import com.practice.design.oss.utils.CacheUtils;
import com.practice.design.oss.utils.RedisUtils;
import com.practice.design.oss.utils.StreamUtils;
import com.practice.design.oss.utils.TenantHelper;
import com.practice.easyexcel.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统oss配置实现 业务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOssConfigServiceImpl implements ISysOssConfigService {

    private final SysOssConfigMapper baseMapper;

    //查库，如果不存在，则将其放入到缓存种
    @Override
    public void init() {
        List<SysOssConfig> list = TenantHelper.ignore(() ->
                baseMapper.selectList(
                        new LambdaQueryWrapper<SysOssConfig>().orderByAsc(TenantEntity::getTenantId))
        );
        Map<String, List<SysOssConfig>> map = StreamUtils.groupByKey(list, SysOssConfig::getTenantId);
        try {
            for (String tenantId : map.keySet()) {
                TenantHelper.setDynamic(tenantId);
                for (SysOssConfig config : map.get(tenantId)) {
                    String configKey = config.getConfigKey();
                    if (Objects.equals("0", config.getConfigKey())) {
                        RedisUtils.setCacheObject("sys_oss:default_config", configKey);
                    }
                    CacheUtils.put("sys_oss_config", config.getConfigKey(), JsonUtils.toJsonString(config));
                }
            }
        } finally {
            TenantHelper.clearDynamic();
        }

    }
}
