package com.formssi.mall.mq.consumer.threadpool;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 利用线程池处理积压消息
 */
@RocketMQMessageListener(topic = "kongquan-threadpool", consumerGroup = "kongquan-threadpool-test-group")
@Component
public class ThreadPoolConsumerService implements RocketMQListener<MessageExt> {

    @Autowired
    private ThreadPoolTaskExecutor messageExecutor;

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("TOPIC:kongquan-simple-test 消费者SimpleConsumerService收到消息：" + JSON.toJSONString(messageExt));
        messageExecutor.submit(new MyWork(messageExt));
    }
}
