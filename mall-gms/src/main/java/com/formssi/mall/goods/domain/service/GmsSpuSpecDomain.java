package com.formssi.mall.goods.domain.service;

import com.formssi.mall.common.entity.resp.CommonPage;

import com.formssi.mall.gms.cmd.GmsSpuSpecCmd;
import com.formssi.mall.gms.cmd.GmsSpuSpecValueCmd;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;

import java.util.List;


public interface GmsSpuSpecDomain {

    /**
     * 保存数据到规格表表中
     * @param gmsSpuSpecCmd
     */
    void saveGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 保存多条数据到规格值表中
     * @param gmsSpuSpecCmd
     */
    void saveGmsSpecValue(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 保存一条数据到规格值表中
     * @param gmsSpuSpecValueCmd
     */
    void saveGmsSpecValue(GmsSpuSpecValueCmd gmsSpuSpecValueCmd);

    /**
     * 根据Id删除对应规格
     * @param id
     */
    void deleteGmsSpec(Long id);

    /**
     * 根据规格id删除对应的规格值
     * @param id
     */
    void deleteGmsSpecValue(Long id);

    /**
     * 修改规格表
     * @param gmsSpuSpecCmd
     */
    void updateGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 修改规格值表
     * @param gmsSpuSpecCmd
     */
    void updateGmsSpecValue(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 批量删除规格
     * @param ids
     */
    void deleteGmsSpecBatch(List<Long> ids);

    /**
     * 根据规格id集合删除对应的规格值
     * @param ids
     */
    void deleteGmsSpecValueBatch(List<Long> ids);

    /**
     * 根据分类id查询对应的规格值
     * @param catalogId
     */
    List<GmsSpuSpecDO> listGmsSpecByCategoryId(Long catalogId);

    /**
     * 根据条件查询规格和规格值
     * @param gmsSpuSpecPageQry
     * @return
     */
    CommonPage<GmsSpuSpecDTO> listGmsSpecAndValuePage(GmsSpuSpecPageQry gmsSpuSpecPageQry);
}
