package com.formssi.mall.mq.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class RocketMqMessage {

    /**
     * 主题 必填
     */
    private String topic;

    /**
     * 标签 必填
     */
    private String tag;

    /**
     * 路由key 必填 发送消息有顺序时这个当hashkey使用
     */
    private String keys;

    /**
     * 发送者群组
     */
    private String producerGroup;

    /**
     * json消息体  必填
     */
    private String message;

    /**
     * 消息模块  必填
     */

    private String messageModule;

    /**
     * 消息延时等级  18个等级
     */
    private int delayLevel;

}
