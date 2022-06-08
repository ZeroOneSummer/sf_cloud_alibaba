package com.formssi.mall.goods.domain.service;

import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author:prms
 * @create: 2022-04-20 15:25
 * @version: 1.0
 */
public interface GmsSkuDomain {
    Future<List<GmsSkuDO>> getSkuDetail(GmsSpuDetailQry qry);
}
