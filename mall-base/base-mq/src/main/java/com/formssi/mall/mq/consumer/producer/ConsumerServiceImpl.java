package com.formssi.mall.mq.consumer.producer;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.formssi.mall.mq.Enum.MqLogEnum;
import com.formssi.mall.mq.mapper.MqLogSuccessMapper;
import com.formssi.mall.mq.pojo.MqLogSuccess;
import com.formssi.mall.mq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 监听消费消息
 */
@Slf4j
@Service
public class ConsumerServiceImpl {

    @Value("${rocketmq.name.producer.topic}")
    private String  rocketmqNameProducerTopic;

    @Value("${rocketmq.name.server.host}")
    private String  rokectMqNameServer;

    @Autowired
    private MqLogSuccessMapper mqLogSuccessMapper;

    @PostConstruct
    public void comsumerMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("kongquan-consumer-test-group-B");
        consumer.setNamesrvAddr(rokectMqNameServer);
        // 主题  key
        consumer.subscribe(rocketmqNameProducerTopic, "*");
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("监听到消息，消费者{}", "kongquan-consumer-test-group-B");
                for (MessageExt mess : msgs) {
                    String message = null;
                    // 消息体
                    byte[] messBody = mess.getBody();
                    // 消息ID
                    log.info("消息id:{}", mess.getMsgId());
                    try {
                        message = new String(messBody, "UTF-8");
                        User rocketMqMessage = JSON.parseObject(message, User.class);
                        log.info("消息体RocketMqMessage:{}", rocketMqMessage);
                        //TODO 做一些业务逻辑处理
                        // 修改消息消费状态 成功
                        UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
                        updateWrapper.eq("msg_id", mess.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_SUCCESS.getMsgStatus());
                        mqLogSuccessMapper.update(null,updateWrapper);
                        // 成功
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        // 修改消息消费状态 失败
                        UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
                        updateWrapper.eq("msg_id", mess.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_FAIL.getMsgStatus());
                        mqLogSuccessMapper.update(null,updateWrapper);
                        // 失败重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return null;
            }
        });

        // 保证顺序消费 MessageListenerOrderly
        /*consumer.setMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for(MessageExt message : list){
                    String str = new String(message.getBody());
                    System.out.println(str);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });*/
        consumer.start();

    }
}
