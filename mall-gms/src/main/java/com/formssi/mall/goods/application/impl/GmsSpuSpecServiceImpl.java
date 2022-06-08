package com.formssi.mall.goods.application.impl;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.cmd.GmsSpuSpecCmd;
import com.formssi.mall.gms.cmd.GmsSpuSpecValueCmd;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.application.GmsSpuSpecService;
import com.formssi.mall.goods.domain.service.GmsSpuSpecDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GmsSpuSpecServiceImpl implements GmsSpuSpecService {

    @Autowired
    private GmsSpuSpecDomain gmsSpuSpecDomain;

    /**
     * 条件查询规格列表
     *
     * @param gmsSpuSpecPageQry
     * @return
     */
    @Override
    public CommonPage<GmsSpuSpecDTO> listGmsSpecPage(GmsSpuSpecPageQry gmsSpuSpecPageQry) {
        CommonPage<GmsSpuSpecDTO> gmsSpuSpecValueDOCommonPage = gmsSpuSpecDomain.listGmsSpecAndValuePage(gmsSpuSpecPageQry);
        return gmsSpuSpecValueDOCommonPage;
    }

    /**
     * 保存商品规格
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void saveGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd) {
        gmsSpuSpecDomain.saveGmsSpec(gmsSpuSpecCmd);
        gmsSpuSpecDomain.saveGmsSpecValue(gmsSpuSpecCmd);
    }

    /**
     * 根据id删除规格
     *
     * @param id
     */
    @Override
    public void deleteGmsSpec(Long id) {
        gmsSpuSpecDomain.deleteGmsSpec(id);
        gmsSpuSpecDomain.deleteGmsSpecValue(id);
    }

    /**
     * 修改规格
     *
     * @param gmsSpuSpecCmd
     */
    @Override
    public void updateGmsSpec(GmsSpuSpecCmd gmsSpuSpecCmd) {
        gmsSpuSpecDomain.updateGmsSpec(gmsSpuSpecCmd);
        gmsSpuSpecDomain.updateGmsSpecValue(gmsSpuSpecCmd);
    }

    /**
     * 批量删除规格
     *
     * @param ids
     */
    @Override
    public void deleteGmsSpecBatch(List<Long> ids) {
        gmsSpuSpecDomain.deleteGmsSpecBatch(ids);
        gmsSpuSpecDomain.deleteGmsSpecValueBatch(ids);
    }

    /**
     * 新增一条规格值
     *
     * @param gmsSpuSpecValueCmd
     */
    @Override
    public void savaGmsSpecValue(GmsSpuSpecValueCmd gmsSpuSpecValueCmd) {
        gmsSpuSpecDomain.saveGmsSpecValue(gmsSpuSpecValueCmd);
    }

}
