package com.formssi.mall.cms.domain.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.cms.cmd.CmsAreaCmd;
import com.formssi.mall.cms.domain.entity.CmsArea;
import com.formssi.mall.cms.domain.repository.CmsAreaRepositoryService;
import com.formssi.mall.cms.domain.service.CmsAreaDomainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsAreaDomainServiceImpl implements CmsAreaDomainService {
    @Autowired
    private CmsAreaRepositoryService cmsAreaRepositoryService;
    @Override
    public Page<CmsArea> getlist() {
        return cmsAreaRepositoryService.pagelist();
    }

    @Override
    public CmsArea selectById(String id) {
        LambdaQueryWrapper<CmsArea> lq = new LambdaQueryWrapper<>();
        lq.eq(CmsArea::getCode,id);
        return cmsAreaRepositoryService.getOne(lq);
    }

    @Override
    public void savelist(List<CmsArea> commonReq) {
        cmsAreaRepositoryService.savelist(commonReq);
    }

    @Override
    public void removeByIds(List<CmsArea> body) {
        for (CmsArea cmsArea : body) {
            cmsAreaRepositoryService.removeById(cmsArea);
        }
    }

    @Override
    public void insert(CmsAreaCmd cmsAreaCmd) {
        CmsArea cmsArea = new CmsArea();
        BeanUtils.copyProperties(cmsAreaCmd, cmsArea);
        cmsAreaRepositoryService.save(cmsArea);
    }

    @Override
    public void deleteById(String parseInt) {
        cmsAreaRepositoryService.removeById(parseInt);
    }

    @Override
    public void updateone(CmsAreaCmd code) {
        CmsArea cmsArea = new CmsArea();
        BeanUtils.copyProperties(code, cmsArea);
        cmsAreaRepositoryService.updateById(cmsArea);
    }

    @Override
    public void updatelist(List<CmsArea> body) {
        cmsAreaRepositoryService.updateBatchById(body);
    }

    @Override
    public List<CmsArea> getOnebyCode(String code) {
        return cmsAreaRepositoryService.getOnebyCode(code);
    }
}
