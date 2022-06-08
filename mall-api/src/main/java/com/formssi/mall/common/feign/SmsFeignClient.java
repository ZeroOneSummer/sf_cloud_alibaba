package com.formssi.mall.common.feign;

import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * @author jiangyaoting
 */
@FeignClient(value = "common-server",path = "/sms")
public interface SmsFeignClient {

    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @GetMapping("/getSMSCaptcha")
    CommonResp getSMSCaptcha(@NotNull String mobile);


    /**
     * 批量短信发送
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendBatchSMS")
    CommonResp sendBatchSMS(@Validated @RequestBody MessageCmd messageCmd);

}
