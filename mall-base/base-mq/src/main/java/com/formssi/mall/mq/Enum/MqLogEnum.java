package com.formssi.mall.mq.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MqLogEnum {
    MESS_ALL("0","消息草稿"),
    MESS_SUCCESS("1","消息发送成功"),
    MESS_CONSUMER_SUCCESS("2","消息消费成功"),
    MESS_CONSUMER_FAIL("3","消息消费失败"),
    MESS_CONSUMER_WUCI_FAIL("5","异常失败消费五次"),
    MESS_FAIL("4","消息发送失败");

    private String msgStatus;
    private String msgDesc;

}
