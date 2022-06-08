package com.formssi.mall.cms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.cms.domain.entity.CmsArea;
import com.formssi.mall.cms.domain.repository.CmsAreaRepositoryService;
import com.formssi.mall.cms.infrastructure.repository.mapper.CmsAreaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsAreaRepositoryServiceImpl extends ServiceImpl<CmsAreaMapper, CmsArea> implements CmsAreaRepositoryService {

    @Override
    public void savelist(List<CmsArea> commonReq) {
        List<CmsArea> body = commonReq;
        this.saveBatch(body);
    }

    @Override
    public Page<CmsArea> pagelist() {
        return this.page(new Page<>(1, 10));
    }

    @Override
    public List<CmsArea> getOnebyCode(String code) {
        LambdaQueryWrapper<CmsArea> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CmsArea::getParentCode,code);
        return this.list(lambdaQueryWrapper);
    }
}