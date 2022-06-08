package com.formssi.mall.cms.application.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.cms.application.CmsAreaService;
import com.formssi.mall.cms.cmd.CmsAreaCmd;
import com.formssi.mall.cms.domain.entity.CmsArea;
import com.formssi.mall.cms.domain.service.CmsAreaDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsAreaServiceImpl implements CmsAreaService {

    @Autowired
    private CmsAreaDomainService cmsAreaDomainService;

    @Override
    public Page<CmsArea> getList() {
        return cmsAreaDomainService.getlist();
    }

    @Override
    public CmsArea getone(String id) {
        return cmsAreaDomainService.selectById(id);
    }

    @Override
    public void saveone(CmsAreaCmd cmsAreaCmd) {
        cmsAreaDomainService.insert(cmsAreaCmd);
    }

    @Override
    public void savelist(List<CmsArea> commonReq) {
        cmsAreaDomainService.savelist(commonReq);
    }

    @Override
    public void deleteone(CmsAreaCmd cmsAreaCmd) {
        cmsAreaDomainService.deleteById(cmsAreaCmd.getCode());
    }

    @Override
    public void deletelist(List<CmsArea> body) {
        cmsAreaDomainService.removeByIds(body);
    }

    @Override
    public void updateone(CmsAreaCmd code) {
        cmsAreaDomainService.updateone(code);
    }

    @Override
    public void updatelist(List<CmsArea> body) {
        cmsAreaDomainService.updatelist(body);
    }

    @Override
    public List<CmsArea> getOnebyCode(String code) {
        return cmsAreaDomainService.getOnebyCode(code);
    }
}
