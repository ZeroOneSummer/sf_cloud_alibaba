package com.formssi.mall.common.feign;

import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * @author jiangyaoting
 */
@FeignClient(name = "common-server", path = "/mail")
public interface EMailFeginClient {

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @GetMapping("/getEMailCaptcha")
    CommonResp getEMailCaptcha(@NotNull String email);

    /**
     * 发送普通邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendEMail")
    CommonResp sendEMail(@RequestBody MessageCmd messageCmd);

    /**
     * 发送活动邮件
     *
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendActiveEMail")
    CommonResp sendActiveEMail(@RequestBody MessageCmd messageCmd);

    /**
     * 预警邮件
     * @param messageCmd
     * @return
     */
    @PostMapping("/sendWarningEMail")
    CommonResp sendWarningEMail(@RequestBody MessageCmd messageCmd);


}
