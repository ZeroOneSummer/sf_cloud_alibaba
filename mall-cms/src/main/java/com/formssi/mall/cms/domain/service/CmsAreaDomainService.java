package com.formssi.mall.cms.domain.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.cms.cmd.CmsAreaCmd;
import com.formssi.mall.cms.domain.entity.CmsArea;

import java.util.List;

public interface CmsAreaDomainService {
    Page<CmsArea> getlist();

    CmsArea selectById(String id);

    void savelist(List<CmsArea> commonReq);

    void removeByIds(List<CmsArea> body);

    void insert(CmsAreaCmd cmsAreaCmd);

    void deleteById(String parseInt);

    void updateone(CmsAreaCmd code);

    void updatelist(List<CmsArea> body);

    List<CmsArea> getOnebyCode(String code);
}