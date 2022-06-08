package com.formssi.mall.common.feign;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.entity.resp.ErrorBody;
import com.formssi.mall.common.entity.resp.RespCode;
import com.formssi.mall.gms.query.GmsSpuQry;
import org.springframework.stereotype.Component;

@Component
public class GmsSpuFeignClientFallBack implements GmsSpuFeignClient{
    @Override
    public CommonResp listSpus(GmsSpuQry gmsSpuQry) {
        return CommonResp.error(RespCode.BAD_REQUEST, ErrorBody.build(new Throwable("服务降级!")));
    }
}
