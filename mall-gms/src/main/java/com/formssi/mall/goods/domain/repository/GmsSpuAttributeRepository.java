package com.formssi.mall.goods.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuAttributeDO;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;

import java.util.List;

public interface GmsSpuAttributeRepository extends IService<GmsSpuAttributeDO> {

    CommonPage<GmsSpuAttributeDO> listSpecPage(GmsSpuAttributePageQry gmsSpuAttributePageQry);

    List<GmsSpuAttributeDO> getAttrList(GmsSpuDetailQry qry);
}
