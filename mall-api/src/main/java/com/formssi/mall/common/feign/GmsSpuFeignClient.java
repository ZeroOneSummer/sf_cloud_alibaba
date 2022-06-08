package com.formssi.mall.common.feign;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.gms.query.GmsSpuQry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "gms-server", path = "/gms",fallback = GmsSpuFeignClientFallBack.class,configuration = FeignConfiguration.class)
public interface GmsSpuFeignClient {

    @PostMapping("/listSpus")
    public CommonResp listSpus(@RequestBody GmsSpuQry gmsSpuQry) ;

}
