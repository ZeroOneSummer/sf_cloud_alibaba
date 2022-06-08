package com.formssi.mall.goods.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;

import java.util.List;

public interface GmsSpuSpecRepository extends IService<GmsSpuSpecDO> {

    CommonPage<GmsSpuSpecDO> listSpecPage(GmsSpuSpecPageQry gmsSpuSpecPageQry);
}
