package com.formssi.mall.goods.application;


import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.cmd.GmsSpuSpecCmd;
import com.formssi.mall.gms.cmd.GmsSpuSpecValueCmd;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;

import java.util.List;

public interface GmsSpuSpecService {

    /**
     * 条件查询规格列表
     * @param gmsSpuSpecPageQry
     * @return
     */
    CommonPage<GmsSpuSpecDTO> listGmsSpecPage(GmsSpuSpecPageQry gmsSpuSpecPageQry);

    /**
     * 保存商品规格
     * @param gmsSpuSpecCmd
     */
    void saveGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 根据id删除规格
     * @param id
     */
    void deleteGmsSpec(Long id);

    /**
     * 修改规格
     * @param gmsSpuSpecCmd
     */
    void updateGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd);

    /**
     * 批量删除规格
     * @param ids
     */
    void deleteGmsSpecBatch(List<Long> ids);

    /**
     * 新增一条规格值
     * @param gmsSpuSpecValueCmd
     */
    void savaGmsSpecValue(GmsSpuSpecValueCmd gmsSpuSpecValueCmd);
}
