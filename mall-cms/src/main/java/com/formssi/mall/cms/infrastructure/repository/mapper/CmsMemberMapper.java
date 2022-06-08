package com.formssi.mall.cms.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.cms.domain.repository.po.CmsMemberPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CmsMemberMapper extends BaseMapper<CmsMemberPO> {
}
