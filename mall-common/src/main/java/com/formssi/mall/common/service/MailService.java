package com.formssi.mall.common.service;

import com.formssi.mall.common.cmd.MessageCmd;

/**
 * @author jiangyaoting
 */
public interface MailService {

    /**
     * 获取邮件验证码
     * @param email
     */
    void getCaptcha(String email);

    /**
     * 发送邮件
     * @param messageCmd
     */
    void sendEMail(MessageCmd messageCmd);

    /**
     * 发送活动邮件
     * @param messageCmd
     */
    void sendActiveEMail(MessageCmd messageCmd);

    /**
     * 预警邮件
     * @param messageCmd
     */
    void sendWarningEMail(MessageCmd messageCmd);
}
