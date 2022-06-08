package com.formssi.mall.goods.domain.service;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.cmd.GmsSkuCmd;
import com.formssi.mall.gms.cmd.GmsSpuCmd;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 后台商品 领域服务类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
public interface IGmsSpuDomainService {
    GmsSpuDO addSpu(GmsSpuCmd gmsSpuCmd);
    void addSku(Long RoleId, List<GmsSkuCmd> roleIds);

    void addOrUpdateSku(Long RoleId, List<GmsSkuCmd> roleIds);

    GmsSpuDO updateSpu(GmsSpuCmd gmsSpuCmd);

    void deleteSkuBySpuId(Long spuId);

    void operateSpu(Integer operateType, List<GmsSpuCmd> gmsSkuCmdList);

    CommonPage<GmsSpuDO> gmsListSpuByCateId(GmsSpuPageByCateQry qry);

    Future<GmsSpuDO> getSpuDetail(GmsSpuDetailQry qry);
}
