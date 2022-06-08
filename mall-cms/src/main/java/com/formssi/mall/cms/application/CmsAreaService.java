package com.formssi.mall.cms.application;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.cms.cmd.CmsAreaCmd;
import com.formssi.mall.cms.domain.entity.CmsArea;

import java.util.List;

public interface CmsAreaService {
    Page<CmsArea> getList();


    CmsArea getone(String id);

    void saveone(CmsAreaCmd cmsAreaCmd);

    void savelist(List<CmsArea> commonReq);

    void deleteone(CmsAreaCmd cmsAreaCmd);

    void deletelist(List<CmsArea> body);

    void updateone(CmsAreaCmd code);

    void updatelist(List<CmsArea> body);


    List<CmsArea> getOnebyCode(String code);
}