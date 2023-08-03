package com.practice.pipe.dao;

import com.practice.pipe.model.MessageTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * 查询消息模板
 */
@Mapper
public interface MessageTemplateDao{
    Optional<MessageTemplate> findById(Long messageTemplateId);
}
