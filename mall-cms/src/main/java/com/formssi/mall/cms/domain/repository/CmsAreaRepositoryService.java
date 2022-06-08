package com.formssi.mall.cms.domain.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.cms.domain.entity.CmsArea;

import java.util.List;

public interface CmsAreaRepositoryService extends IService<CmsArea> {

    void savelist(List<CmsArea> commonReq);

    Page<CmsArea> pagelist();

    List<CmsArea> getOnebyCode(String code);
}
