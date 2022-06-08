package com.formssi.mall.mq;

import com.formssi.mall.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class News {
    //接收人
    private List<String> Receiver;

    //邮件主题
    private String subject;

    //邮件内容
    private String text;

    //图片
    private List<String> images;

    //消息类型
    private MessageType messageType;
}
