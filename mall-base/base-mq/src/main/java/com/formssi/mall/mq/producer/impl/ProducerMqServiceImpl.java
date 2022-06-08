package com.formssi.mall.mq.producer.impl;


import com.formssi.mall.mq.Enum.MqLogEnum;
import com.formssi.mall.mq.mapper.MqLogAllMapper;
import com.formssi.mall.mq.mapper.MqLogFailMapper;
import com.formssi.mall.mq.mapper.MqLogSuccessMapper;
import com.formssi.mall.mq.pojo.MqLogAll;
import com.formssi.mall.mq.pojo.MqLogFail;
import com.formssi.mall.mq.pojo.MqLogSuccess;
import com.formssi.mall.mq.pojo.RocketMqMessage;
import com.formssi.mall.mq.producer.ProducerMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProducerMqServiceImpl implements ProducerMqService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private MqLogSuccessMapper mqLogSuccessMapper;

    @Autowired
    private MqLogFailMapper mqLogFailMapper;

    @Autowired
    private MqLogAllMapper mqLogAllMapper;

    @Value("${rocketmq.name.server.host}")
    private String rokectMqNameServer;

    @Value("${rocketmq.retryTimesWhenSendFailed}")
    private int retryTimesWhenSendFailed;


    @Override
    public SendResult syncSend(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接 自动解析
        SendResult sendResult = rocketMQTemplate.syncSend(message.getTopic() + ":" + message.getTag(), message.getMessage());
        // 记录到数据库 成功
        MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
        mqLogSuccessMapper.insert(mqLogSuccess);
        log.info("syncSend发送成功");


        return sendResult;
    }

    @Override
    public SendResult syncSendDelay(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接 自动解析
        // 发送延时消息 注意这个Message 是org.springframework.messaging的 不是rocketmq的
        //MessageBuilder.withPayload(message.getMessage()).build();
        // 4 == 延时30s
        // 3000L == 发送超时时间
        SendResult sendResult = rocketMQTemplate.syncSend(message.getTopic() + ":" + message.getTag(), MessageBuilder.withPayload(message.getMessage()).build(), 3000L, message.getDelayLevel());
        // 记录到数据库 成功
        MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
        mqLogSuccessMapper.insert(mqLogSuccess);
        log.info("syncSend发送延时消息成功");
        return null;
    }

    @Override
    public SendResult syncSendOrderly(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(message.getTopic() + ":" + message.getTag(), message.getMessage(), message.getKeys());
        // 记录到数据库 成功
        MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
        mqLogSuccessMapper.insert(mqLogSuccess);
        log.info("syncSendOrderly发送成功");
        return sendResult;
    }

    @Override
    public void asyncSend(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接
        rocketMQTemplate.asyncSend(message.getTopic() + ":" + message.getTag(), message.getMessage(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 记录到数据库 成功
                MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
                mqLogSuccessMapper.insert(mqLogSuccess);
                log.info("异步发送成功{}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                // 记录到数据库 失败
                MqLogFail mqLogFail = bulidMqLogFail(message);
                mqLogFailMapper.insert(mqLogFail);
                log.info("异步发送失败{}", e.getMessage());

            }
        });
    }

    @Override
    public void asyncSendOrderly(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接
        rocketMQTemplate.asyncSendOrderly(message.getTopic() + ":" + message.getTag(), message.getMessage(), message.getKeys(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 记录到数据库 成功
                MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
                mqLogSuccessMapper.insert(mqLogSuccess);
                log.info("异步顺序发送成功{}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                // 记录到数据库 失败
                MqLogFail mqLogFail = bulidMqLogFail(message);
                mqLogFailMapper.insert(mqLogFail);
                log.info("异步发送失败{}", e.getMessage());

            }
        });

    }

    @Override
    public void sendOneWay(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接
        rocketMQTemplate.sendOneWay(message.getTopic() + ":" + message.getTag(), message.getMessage());
        log.info("sendOneWay发送成功");
    }

    @Override
    public void convertAndSend(RocketMqMessage message) throws Exception {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 发送消息 tags在topic之后用：拼接
        rocketMQTemplate.convertAndSend(message.getTopic() + ":" + message.getTag(), message.getMessage());
        log.info("convertAndSend发送成功");
    }

    @Override
    public boolean producerSend(RocketMqMessage message) throws MQClientException, RemotingException, InterruptedException {
        // 消息埋点
        MqLogAll mqLogAll = bulidMqLogAll(message);
        mqLogAllMapper.insert(mqLogAll);
        // 连接消息服务器
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(rokectMqNameServer);
        producer.setProducerGroup(message.getProducerGroup());
        // 失败重试次数
        producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        producer.start();
        Message msg = new Message(message.getTopic(), message.getTag(), message.getKeys(), message.getMessage().getBytes());
        // 设置延时等级  1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        // 4 == 30s
        msg.setDelayTimeLevel(4);
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 记录到数据库 成功
                MqLogSuccess mqLogSuccess = bulidMqLogSuccess(message, sendResult);
                mqLogSuccessMapper.insert(mqLogSuccess);
                log.info("消息发送成功：{}，消息体：{}", message.getTopic(), message);
            }

            @Override
            public void onException(Throwable e) {
                // 记录到数据库 失败
                MqLogFail mqLogFail = bulidMqLogFail(message);
                mqLogFailMapper.insert(mqLogFail);
                log.info("消息发送失败：{}，消息体：{}", message.getTopic(), message);
            }
        });

        // send方法里的最后一个参数会被传到select方法的最后一个参数，选择queue可以根据数据的特征，也可以像这样直接指定一个队列
        /*try {
            producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    return list.get(0);
                }
            }, 0);
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }*/

        TimeUnit.MILLISECONDS.sleep(1000);
        // 关闭
        producer.shutdown();
        return true;
    }

    /**
     * 总消息组装
     *
     * @param message
     * @return
     */
    private MqLogAll bulidMqLogAll(RocketMqMessage message) {
        MqLogAll mqLogAll = MqLogAll.builder()
                .msgBody(message.getMessage())
                .msgKeys(message.getKeys())
                .msgModule(message.getMessageModule())
                .msgStatus(MqLogEnum.MESS_ALL.getMsgStatus())
                .msgTags(message.getTag())
                .msgTopic(message.getTopic())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        return mqLogAll;
    }

    /**
     * 发送成功消息组装
     *
     * @param message
     * @param sendResult
     * @return
     */
    private MqLogSuccess bulidMqLogSuccess(RocketMqMessage message, SendResult sendResult) {
        MqLogSuccess mqLogSuccess = MqLogSuccess.builder().msgBody(message.getMessage())
                .msgId(sendResult.getMsgId())
                .msgOffset(sendResult.getOffsetMsgId())
                .msgTopic(message.getTopic())
                .msgKeys(message.getKeys())
                .msgTags(message.getTag())
                .msgModule(message.getMessageModule())
                .msgStatus(MqLogEnum.MESS_SUCCESS.getMsgStatus())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        return mqLogSuccess;
    }


    /**
     * 发送消息失败组装
     *
     * @param message
     * @return
     */
    private MqLogFail bulidMqLogFail(RocketMqMessage message) {
        MqLogFail mqLogFail = MqLogFail.builder()
                .msgBody(message.getMessage())
                .msgKeys(message.getKeys())
                .msgStatus(MqLogEnum.MESS_FAIL.getMsgStatus())
                .msgTags(message.getTag())
                .msgTopic(message.getTopic())
                .msgModule(message.getMessageModule())
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        return mqLogFail;
    }
}
