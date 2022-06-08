package com.formssi.mall.common.controller;

import com.formssi.mall.common.service.MailService;
import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author jiangyaoting
 */
@RestController
@RequestMapping("/mail")
public class EMailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @GetMapping("/getEMailCaptcha")
    public CommonResp getEMailCaptcha(@NotNull String email){
        mailService.getCaptcha(email);
        return CommonResp.ok();
    }

    /**
     * 发送普通邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendEMail")
    public CommonResp sendEMail(@Validated @RequestBody MessageCmd messageCmd){
        mailService.sendEMail(messageCmd);
        return CommonResp.ok();
    }

    /**
     * 发送活动邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendActiveEMail")
    public CommonResp sendActiveEMail(@Validated @RequestBody MessageCmd messageCmd){
        mailService.sendActiveEMail(messageCmd);
        return CommonResp.ok();
    }

    /**
     * 预警邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendWarningEMail")
    public CommonResp sendWarningEMail(@Validated @RequestBody MessageCmd messageCmd){
        mailService.sendWarningEMail(messageCmd);
        return CommonResp.ok();
    }

}
