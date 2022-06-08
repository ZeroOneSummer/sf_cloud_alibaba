package com.formssi.mall.order.feign;

import com.formssi.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("seata-account")
public interface AccountFeignService {
    @GetMapping("/account/deduct")
    R deduct(@RequestParam("userId") Integer userId, @RequestParam("amount") Integer amount,
             @RequestParam("errorPhase") Integer errorPhase);

    @GetMapping("/account/tccDeduct")
    R tccDeduct(@RequestParam("userId") Integer userId, @RequestParam("amount") Integer amount,
                @RequestParam("errorPhase") Integer errorPhase);
}
