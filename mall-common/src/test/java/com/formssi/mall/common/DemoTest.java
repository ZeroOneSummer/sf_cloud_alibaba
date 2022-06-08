package com.formssi.mall.common;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiangyaoting
 */
@Service
public class DemoTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void Demo_Test(){
        List<String> list = new ArrayList<String>();
        list.add("s1");
        list.add("s2");
        list.add("s3");
            System.out.println("arr = " + list.toArray());
        System.out.println("StringUtils.join(list) = " + StringUtils.join(list));
    }

    @Test
    public void commonEmail() {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom("jiangyaoting@formssi.com");
        //谁要接收
        message.setTo("dengyuanhao@formssi.com");
        //邮件标题
        message.setSubject("大风歌");
        //邮件内容
        message.setText("大风起兮云飞扬，威加海内兮归故乡。安得猛士兮守四方！");
        try {
            mailSender.send(message);
            System.out.println("发送成功");
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
