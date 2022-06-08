package com.formssi.mall.goods.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.cmd.GmsSpuCmd;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuQry;
import com.formssi.mall.gms.query.GmsSpuSearchQry;
import com.formssi.mall.gms.vo.GmsSpuDetailVO;

import java.util.List;

/**
 * <p>
 * 后台商品 应用服务类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */

public interface IGmsSpuService {
    CommonPage<GmsSpuDTO> listSpus(GmsSpuQry gmsSpuQry);
    void addSpu(GmsSpuCmd gmsSpuCmd);

    void updateSpu(GmsSpuCmd gmsSpuCmd);

    void operateSpu(Integer operateType, List<GmsSpuCmd> gmsSpuCmdList);

    GmsSpuDetailVO spuDetail(GmsSpuDetailQry req);

    List<GmsSpuDTO> searchSpuBykey(GmsSpuSearchQry req);
}
