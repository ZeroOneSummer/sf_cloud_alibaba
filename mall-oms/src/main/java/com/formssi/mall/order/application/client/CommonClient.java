package com.formssi.mall.order.application.client;

import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@FeignClient(value = "common-server")
public interface CommonClient {

    /**
     * 批量短信发送
     * @param messageCmd
     * @return
     */
    @PostMapping("/sms/sendBatchSMS")
    CommonResp sendBatchSMS(@Validated @RequestBody MessageCmd messageCmd);


    /**
     * 发送普通邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/mail/sendEMail")
    CommonResp sendEMail(@Validated @RequestBody MessageCmd messageCmd);
}
