package com.formssi.mall.mq;

import com.alibaba.fastjson.JSON;
import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.enums.MessageType;
import com.formssi.mall.common.feign.EMailFeginClient;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.List;
@Service
public class Comsumer {
    @Autowired
    private EMailFeginClient eMailFeginClient;
    @PostConstruct
    public  void comsumer()throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_comsumer_group");
        consumer.setNamesrvAddr("10.207.0.164:9876");
        //第二个参数指定标签多少 可* 可||和 可指定名称
        consumer.subscribe("boot2", "*");
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : msgs) {
                        byte[] body = messageExt.getBody();
                        String mess = null;
                        try {
                            mess = new String(body, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        News news = JSON.parseObject(mess, News.class);
                        MessageType messageType = news.getMessageType();
                        switch (messageType) {
                            case MESSAGE_TYPE:
                                List<String> receiver = news.getReceiver();
                                for (String s : receiver) {
                                    eMailFeginClient.getEMailCaptcha(s);
                                }
                                break;
                            case MAILMESSAGE_TYPE:
                                MessageCmd messageCmd2 = new MessageCmd();
                                BeanUtils.copyProperties(news,messageCmd2);
                                eMailFeginClient.sendEMail(messageCmd2);
                                break;
                            case Advance_TYPE:
                                MessageCmd messageCmd3 = new MessageCmd();
                                BeanUtils.copyProperties(news,messageCmd3);
                                messageCmd3.setTos(news.getReceiver());
                                eMailFeginClient.sendActiveEMail(messageCmd3);
                                break;
                            case WARNING_TYPE:
                                MessageCmd messageCmd4 = new MessageCmd();
                                BeanUtils.copyProperties(news,messageCmd4);
                                eMailFeginClient.sendWarningEMail(messageCmd4);
                                break;
                        }
                    }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
