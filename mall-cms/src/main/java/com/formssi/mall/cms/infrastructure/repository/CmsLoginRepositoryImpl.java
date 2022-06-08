package com.formssi.mall.cms.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.cms.domain.repository.CmsLoginRepository;
import com.formssi.mall.cms.domain.repository.po.CmsMemberPO;
import com.formssi.mall.cms.infrastructure.repository.mapper.CmsMemberMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CmsLoginRepositoryImpl extends ServiceImpl<CmsMemberMapper, CmsMemberPO> implements CmsLoginRepository {
}
