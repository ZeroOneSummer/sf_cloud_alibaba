package com.formssi.mall.goods.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;

import java.util.List;

public interface GmsSpuAttributeService {
    /**
     * 查询商品属性列表
     * @param gmsSpuAttributePageQry
     * @return
     */
    CommonPage<GmsSpuAttributeDTO> listSpuAttributePage(GmsSpuAttributePageQry gmsSpuAttributePageQry);

    /**
     * 保存商品属性
     * @param gmsSpuAttributeDTO
     */
    void saveSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO);

    /**
     * 根据id删除商品属性
     * @param id
     */
    void deleteSpuAttribute(Long id);

    /**
     * 修改商品属性
     * @param gmsSpuAttributeDTO
     */
    void updateSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO);

    /**
     * 批量删除商品属性
     * @param ids
     */
    void deleteSpuAttributeBatch(List<Long> ids);
}
