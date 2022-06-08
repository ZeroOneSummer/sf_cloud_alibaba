package com.formssi.mall.goods.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;

/**
 * <p>
 * 后台商品 仓库类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
public interface IGmsSpuRepository extends IService<GmsSpuDO> {


    CommonPage<GmsSpuDO> gmsListSpuByCateId(GmsSpuPageByCateQry cateId);


    GmsSpuDO getSpuDetail(GmsSpuDetailQry qry);
}
