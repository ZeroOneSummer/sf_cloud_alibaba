package com.formssi.mall.common.service;

import com.formssi.mall.common.cmd.MessageCmd;

/**
 * @author jiangyaoting
 */
public interface SmsService {

    /**
     * 获得短信
     * @param mobile
     */
    void getSMSCaptcha(String mobile);

    /**
     * 批量短信发送
     * @param messageCmd
     */
    void sendBatchSMS(MessageCmd messageCmd);
}
