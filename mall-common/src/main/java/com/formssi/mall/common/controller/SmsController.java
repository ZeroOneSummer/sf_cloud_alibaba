package com.formssi.mall.common.controller;

import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author jiangyaoting
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;


    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @GetMapping("/getSMSCaptcha")
    public CommonResp getSMSCaptcha(@NotNull String mobile){
        smsService.getSMSCaptcha(mobile);
        return CommonResp.ok();
    }

    /**
     * 批量短信发送
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendBatchSMS")
    public CommonResp sendBatchSMS(@Validated @RequestBody MessageCmd messageCmd){
        smsService.sendBatchSMS(messageCmd);
        return CommonResp.ok();
    }

}
