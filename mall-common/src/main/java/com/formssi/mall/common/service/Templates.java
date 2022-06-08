package com.formssi.mall.common.service;

import com.formssi.mall.common.cmd.MessageCmd;

import java.util.List;

/**
 * @author jiangyaoting
 */
public interface Templates {

    /**
     * 获得验证码模板
     * @param email
     * @param code
     * @param timeout
     * @return
     */
    String getCaptchaTempl(String email, String code, int timeout);

    /**
     * 获得活动模板
     * @param email
     * @param type
     * @return
     */
    String getActiveTempl(String email, String subject, String text, List<String> images, String type);


    /**
     * 获得预警模板
     * @param email
     * @param type
     * @return
     */
    String getWarningTempl(String email, String subject, String text, List<String> images, String type);
}
