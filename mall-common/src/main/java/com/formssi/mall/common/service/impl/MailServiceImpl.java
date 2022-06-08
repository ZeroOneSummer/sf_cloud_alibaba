package com.formssi.mall.common.service.impl;

import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.constant.common.EMailConstant;
import com.formssi.mall.common.service.MailService;
import com.formssi.mall.common.service.Templates;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangyaoting
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Templates templates;

    @Value("${spring.mail.username}")
    private String sendUserName;

    /**
     * 发送验证码
     * @param email
     */
    @Override
    public void getCaptcha(String email) {
        String code = (String) redisService.get(EMailConstant.CODE_EMAIL_ +email);
        if (StringUtils.isNotEmpty(code)){
            throw new BusinessException("上一个验证码未失效!");
        }
        try {
            //生成验证码
            code = RandomStringUtils.randomAlphanumeric(6);
            //创建邮件消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
            minehelper.setFrom(sendUserName);
            minehelper.setTo(email);
            minehelper.setSubject("验证码");
            String html = templates.getCaptchaTempl(email,code,5);
            minehelper.setText(html,true);
            mailSender.send(message);
            redisService.set(EMailConstant.CODE_EMAIL_ +email,code,5, TimeUnit.MINUTES);
        } catch (MailException | MessagingException e) {
           throw new BusinessException("验证码发送失败!");
        }
    }

    /**
     * 发送邮件
     *
     * @param messageCmd
     */
    @Override
    public void sendEMail(MessageCmd messageCmd) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sendUserName); //设置发送邮件账号
            simpleMailMessage.setTo(messageCmd.getTos().toArray(new String[messageCmd.getTos().size()])); //设置接收邮件的人，可以多个
            simpleMailMessage.setSubject(messageCmd.getSubject()); //设置发送邮件的主题
            simpleMailMessage.setText(messageCmd.getText()); //设置发送邮件的内容
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new BusinessException("邮件发送失败!");
        }
    }

    /**
     * 发送活动邮件
     *
     * @param messageCmd
     */
    @Override
    public void sendActiveEMail(MessageCmd messageCmd) {
        for (String to : messageCmd.getTos()) {
            send(messageCmd, to,EMailConstant.ACTIVE_EMAIL_TYPE);
        }
    }

    /**
     * 预警邮件
     *
     * @param messageCmd
     */
    @Override
    public void sendWarningEMail(MessageCmd messageCmd) {
        for (String to : messageCmd.getTos()) {
            send(messageCmd, to,EMailConstant.WARNING_EMAIL_TYPE);
        }
    }

    @Async
    public void send(MessageCmd messageCmd, String to, String type) {
        try {
            String html = null;
            //创建邮件消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper minehelper = new MimeMessageHelper(message, true,"utf-8");
            minehelper.setFrom(sendUserName);
            minehelper.setTo(to);
            minehelper.setSubject(messageCmd.getSubject());
            if (type.contains("活动")){
                html = templates.getActiveTempl(to,messageCmd.getSubject(),messageCmd.getText(),messageCmd.getImages(),type);
            }else {
                html = templates.getWarningTempl(to, messageCmd.getSubject(), messageCmd.getText(), messageCmd.getImages(),type);
            }
            minehelper.setText(html,true);
            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new BusinessException("邮件发送失败!");
        }
    }


}
