package com.practice.design.factory;

import com.practice.design.factory.entity.PayOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderMapper{


    PayOrderDO selectById(Long id);
}
