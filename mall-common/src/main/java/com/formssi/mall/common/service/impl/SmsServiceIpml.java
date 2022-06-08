package com.formssi.mall.common.service.impl;

import com.formssi.mall.autoconfig.template.SmsTemplate;
import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.constant.common.SMSConstant;
import com.formssi.mall.common.service.SmsService;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.redis.service.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiangyaoting
 */
@Service
public class SmsServiceIpml implements SmsService {

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private RedisService redisService;


    /**
     * 获得短信
     *
     * @param mobile
     */
    @Override
    public void getSMSCaptcha(String mobile) {
        String code = (String) redisService.get(SMSConstant.SMS_CODE_KEY + mobile);
        if (StringUtils.isNotEmpty(code)){
            throw new BusinessException("上一个验证码未失效!");
        }
        try {
            //生成验证码
            code = RandomStringUtils.randomAlphanumeric(6);
            smsTemplate.sendSms(mobile,code);
            redisService.set(SMSConstant.SMS_CODE_KEY + mobile,code);
        } catch (Exception e) {
            switch (e.getMessage()){
                case "isv.SMS_CONTENT_ILLEGAL": //短信内容包含禁止发送内容。
                    throw new BusinessException("短信内容包含禁止发送内容");
                case "isv.MOBILE_NUMBER_ILLEGAL": //手机号码格式错误。
                    throw new BusinessException("手机号码格式错误");
                case "isv.OUT_OF_SERVICE": //余额不足
                    throw new BusinessException("余额不足");
                default:
                    throw new BusinessException("系统错误,请联系网站运维人员!");
            }
        }
    }


    /**
     * 批量短信发送
     *
     * @param messageCmd
     */
    @Override
    public void sendBatchSMS(MessageCmd messageCmd) {
        try {
            smsTemplate.sendBatchSms(StringUtils.join(messageCmd.getTos()), messageCmd.getText());
        } catch (Exception e) {
            switch (e.getMessage()){
                case "isv.SMS_CONTENT_ILLEGAL": //短信内容包含禁止发送内容。
                    throw new BusinessException("短信内容包含禁止发送内容");
                case "isv.MOBILE_NUMBER_ILLEGAL": //手机号码格式错误。
                    throw new BusinessException("手机号码格式错误");
                case "isv.OUT_OF_SERVICE": //余额不足
                    throw new BusinessException("余额不足");
                default:
                    throw new BusinessException("系统错误,请联系网站运维人员!");
            }
        }
    }
}
