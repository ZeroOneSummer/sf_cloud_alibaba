package com.formssi.mall.common.feign;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.flash.query.FlashActQry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "flash-server", path = "/flash" )
public interface MallFlashFeignClient {

    /**
     * 查询正在进行中或者即将开始的活动（当前时间小于活动结束时间），且倒序排列。
     * @param flashActQry 查询参数，包含活动名称等
     * @return
     */
    @PostMapping("/listActs")
    public CommonResp listActs(@RequestBody FlashActQry flashActQry);

}