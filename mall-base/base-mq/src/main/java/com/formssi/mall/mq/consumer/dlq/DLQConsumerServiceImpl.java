package com.formssi.mall.mq.consumer.dlq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.formssi.mall.mq.Enum.MqLogEnum;
import com.formssi.mall.mq.mapper.MqLogSuccessMapper;
import com.formssi.mall.mq.pojo.MqLogSuccess;
import com.formssi.mall.mq.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 消息超时，消息重试，将消息弄到死信队列中
 */

/**
 * 消费端重试 ：异常重试、超时重试
 */

/**
 * 死信队列:当重试的次数达到设定值，就会将消息丢入到死信队列
 */
@Slf4j
@Service
public class DLQConsumerServiceImpl {

    @Value("${rocketmq.name.dlq.topic}")
    private String  rocketmqNameProducerTopic;

    @Value("${rocketmq.name.consumer.dlq.group}")
    private String  rocketmqNameConsumerDlqGroup;

    @Value("${rocketmq.name.server.host}")
    private String  rokectMqNameServer;

    @Autowired
    private MqLogSuccessMapper mqLogSuccessMapper;

    // 异常最大重试次数之后就不重试了
    private final int ROCKETMQ_MAX_RETRY_NUM = 5;

    //设置消费者重试次数
    private final int ROCKETMQ_MAX_RECONSUMERE_TIMES= 6;

    // 设置消费者消费超时时间 分钟
    private final long CONSUMER_TIMEOUT_MINUTES = 1L;

    @PostConstruct
    public void comsumerMessage() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketmqNameConsumerDlqGroup);
        consumer.setNamesrvAddr(rokectMqNameServer);
        // 主题  key
        consumer.subscribe(rocketmqNameProducerTopic, "*");
        // 设置集群消费模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置消费的超时时间 分钟
        consumer.setConsumeTimeout(CONSUMER_TIMEOUT_MINUTES);
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(ROCKETMQ_MAX_RECONSUMERE_TIMES);
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("监听到消息，消费者{}", rocketmqNameConsumerDlqGroup);
                for (MessageExt mess : msgs) {
                    log.info("打印消息所有信息：{}",mess);
                    String message = null;
                    // 消息体
                    byte[] messBody = mess.getBody();
                    // 消息ID
                    log.info("消息id:{}", mess.getMsgId());
                    // TODO 根据消息id或者业务id做幂等
                    try {
                        message = new String(messBody, "UTF-8");
                        User rocketMqMessage = JSON.parseObject(message, User.class);
                        log.info("消息体RocketMqMessage:{}", rocketMqMessage);
                        log.info("重试的次数：{}", mess.getReconsumeTimes());
                        //TODO 做一些业务逻辑处理
                        // 修改消息消费状态 成功
                        UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
                        updateWrapper.eq("msg_id", mess.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_SUCCESS.getMsgStatus()).set("update_time", new Date());
                        mqLogSuccessMapper.update(null,updateWrapper);

                        // TODO 模拟
                        // 制造异常导致消费失败 模拟业务异常
                        //int i = 1/0;
                        // 前面设置的消费者超时时间为一分钟 、设置休眠两分钟
                        //TimeUnit.MINUTES.sleep(2);
                        // 设置休眠70s
                        //TimeUnit.MILLISECONDS.sleep(1000*70);

                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e ) { // 捕获最大异常
                        log.error("{}：消息消费失败：{}",rocketmqNameProducerTopic,e.getMessage());
                        UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
                        // 消息已经重试的次数
                        int reconsumeTimes = mess.getReconsumeTimes();
                        // 重试次数大于等于设定的值
                        if(reconsumeTimes >= ROCKETMQ_MAX_RETRY_NUM){
                            // 重试次数大于五次了不重试了
                            updateWrapper.eq("msg_id", mess.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_WUCI_FAIL.getMsgStatus()).set("update_time", new Date());
                            mqLogSuccessMapper.update(null,updateWrapper);
                            // 注意标记不重试了 距离数据库中
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }else{
                            // 修改消息消费状态 失败重试
                            updateWrapper.eq("msg_id", mess.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_FAIL.getMsgStatus()).set("update_time", new Date());
                            mqLogSuccessMapper.update(null,updateWrapper);
                        }
                        // 失败稍后重试
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return null;
            }
        });

        consumer.start();

    }
}
