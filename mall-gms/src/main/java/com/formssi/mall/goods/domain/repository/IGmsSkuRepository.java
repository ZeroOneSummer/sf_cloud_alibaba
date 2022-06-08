package com.formssi.mall.goods.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;

import java.util.List;

/**
 * <p>
 * 后台角色 仓库类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
public interface IGmsSkuRepository extends IService<GmsSkuDO> {


    List<GmsSkuDO> getSkuDetail(GmsSpuDetailQry qry);
}