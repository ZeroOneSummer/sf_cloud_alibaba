package com.formssi.mall.goods.domain.service;

import com.formssi.mall.goods.domain.entity.GmsCategoryDo;

import java.util.List;

/**
 * 领域层
 * 商品分类
 */
public interface GmsCategoryDomain {

    /**
     * 查询分类树
     *
     * @return
     */
    List<GmsCategoryDo> listCate();

}
