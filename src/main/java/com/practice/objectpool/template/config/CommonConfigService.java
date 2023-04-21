package com.practice.objectpool.template.config;

import java.util.List;

import com.practice.objectpool.template.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通用配置服务
 * 
 * @author yongfeigao
 * @date 2018年10月17日
 */
@Service
public class CommonConfigService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CommonConfigDao commonConfigDao;
    
    /**
     * 查询配置
     * 
     */
    public Result<List<CommonConfig>> query() {
        List<CommonConfig> list = null;
        try {
            list = commonConfigDao.select();
        } catch (Exception e) {
            logger.error("query err", e);
            return Result.getDBErrorResult(e);
        }
        return Result.getResult(list);
    }
}
