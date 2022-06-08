package com.formssi.mall.cms.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.formssi.mall.cms.domain.repository.CmsSmsMessageRepository;

import com.formssi.mall.cms.domain.repository.po.CmsSmsMessagePO;

import com.formssi.mall.cms.infrastructure.repository.mapper.CmsSmsMessageMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CmsSmsMessageRepositoryImpl extends ServiceImpl<CmsSmsMessageMapper, CmsSmsMessagePO> implements CmsSmsMessageRepository {
}
