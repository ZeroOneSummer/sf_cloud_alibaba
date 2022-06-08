package com.formssi.mall.xxljob.jobHandler;

import com.formssi.mall.common.feign.MallFlashFeignClient;
import com.formssi.mall.flash.query.FlashActQry;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FlashXxlJob {

    @Autowired
    MallFlashFeignClient mallFlashFeignClient;


    /**
     * 1、查看秒杀活动
     */
    @XxlJob("findFlashHandler")
    public void findFlashHandler() throws Exception {
        log.info("start find Flash！");
        mallFlashFeignClient.listActs(new FlashActQry());
    }
}
