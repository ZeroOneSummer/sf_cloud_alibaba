package com.formssi.mall.goods.domain.repository;

import com.formssi.mall.goods.domain.entity.GmsCategoryDo;

import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-19 17:03
 * @version: 1.0
 */
public interface GmsCategoryRepository {

    /**
     * 查询所有分类
     * @return
     */
    List<GmsCategoryDo> listCate();

}
