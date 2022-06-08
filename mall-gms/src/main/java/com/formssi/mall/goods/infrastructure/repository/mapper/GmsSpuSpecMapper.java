package com.formssi.mall.goods.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;
import com.formssi.mall.goods.domain.repository.po.GmsSpuSpecAndValuePO;

import java.util.List;

public interface GmsSpuSpecMapper extends BaseMapper<GmsSpuSpecDO> {

    /**
     * 规格表和规格值表关联查询
     * @param gmsSpuSpecPageQry
     * @return
     */
    List<GmsSpuSpecAndValuePO> listSpuSpecPage(GmsSpuSpecPageQry gmsSpuSpecPageQry);

    /**
     * 获取规格表数量
     * @return
     */
    Long getCount();

}
