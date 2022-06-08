package com.formssi.mall.goods.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.dto.GmsCategoryDTO;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsCategoryPageQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;

import java.util.List;

/**
 *
 */
public interface GmsCategoryService {

    /**
     * 查询商品分类树
     *
     * @param qry
     * @return
     */
    List<GmsCategoryDTO> cateTreeNode(GmsCategoryPageQry qry);

    CommonPage<GmsSpuDTO> gmsListSpuByCateId(GmsSpuPageByCateQry body);
}
