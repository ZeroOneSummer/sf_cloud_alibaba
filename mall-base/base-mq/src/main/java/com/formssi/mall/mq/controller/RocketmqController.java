package com.formssi.mall.mq.controller;

import com.alibaba.fastjson.JSON;

import com.formssi.mall.mq.pojo.RocketMqMessage;
import com.formssi.mall.mq.pojo.User;
import com.formssi.mall.mq.producer.ProducerMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("rocketmq")
public class RocketmqController {

    @Autowired
    private ProducerMqService producerMqService;

    @Value("${rocketmq.name.sync.topic}")
    private String rocketmqNameSyncTopic;

    @Value("${rocketmq.name.sync.topic.order}")
    private String rocketmqNameSyncTopicOrder;

    @Value("${rocketmq.name.async.topic}")
    private String rocketmqNameAsyncTopic;

    @Value("${rocketmq.name.async.topic.order}")
    private String rocketmqNameAsyncTopicOrder;

    @Value("${rocketmq.name.oneway.topic}")
    private String rocketmqNameOnewayTopic;

    @Value("${rocketmq.name.simple.topic}")
    private String rocketmqNameSimpleTopic;

    @Value("${rocketmq.name.producer.topic}")
    private String rocketmqNameProducerTopic;

    @Value("${rocketmq.name.dlq.topic}")
    private String rocketmqNameDlqopic;

    @Value("${rocketmq.name.delay.topic}")
    private String rocketmqNameDelayTopic;

    /**
     * 同步发送
     * 页面访问http://localhost:8088/rocketmq/sync
     *
     * @throws Exception
     */
    @GetMapping("sync")
    public void sync() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-sync-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameSyncTopic)
                .tag("sync")
                .keys("*")
                .message(JSON.toJSONString(user))
                .messageModule("sync")
                .build();
        SendResult sendResult = producerMqService.syncSend(message);
        log.info("同步发送字符串{}, 发送结果{}", JSON.toJSON(message), sendResult);
    }

    /**
     * 同步发送 带顺序消费
     * 页面访问http://localhost:8088/rocketmq/sync
     *
     * @throws Exception
     */
    @GetMapping("sync/order")
    public void syncOrder() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("=====================>订单创建");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameSyncTopicOrder)
                .tag("sync-order")
                .keys("123456789")
                .message(JSON.toJSONString(user))
                .messageModule("sync-order")
                .build();
        SendResult sendResult = producerMqService.syncSendOrderly(message);
        log.info("同步发送字符串{}, 发送结果{}", JSON.toJSON(message), sendResult);

        User user1 = new User();
        user1.setEmail("kongquanhj@163.com");
        user1.setName("=================>订单支付");
        user1.setAge(18);
        user1.setCreateTime(LocalDateTime.now());
        RocketMqMessage message1 = RocketMqMessage.builder()
                .topic(rocketmqNameSyncTopicOrder)
                .tag("sync-order")
                .keys("123456789")
                .message(JSON.toJSONString(user1))
                .messageModule("sync-order")
                .build();
        SendResult sendResult1 = producerMqService.syncSendOrderly(message1);
        log.info("同步发送字符串{}, 发送结果{}", JSON.toJSON(message1), sendResult1);
    }

    /**
     * 异步发送
     * 页面访问http://localhost:8088/rocketmq/async
     *
     * @throws Exception
     */
    @GetMapping("async")
    public void async() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-async-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameAsyncTopic)
                .tag("async")
                .keys("*")
                .message(JSON.toJSONString(user))
                .messageModule("async")
                .build();
        producerMqService.asyncSend(message);
        log.info("异步发送字符串{}", JSON.toJSON(message));
    }

    /**
     * 异步发送
     * 页面访问http://localhost:8088/rocketmq/async
     *
     * @throws Exception
     */
    @GetMapping("async/order")
    public void asyncOrder() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("=============>订单创建");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameAsyncTopicOrder)
                .tag("async-order")
                .keys("888888888888")
                .message(JSON.toJSONString(user))
                .messageModule("async-order")
                .build();
        producerMqService.asyncSendOrderly(message);
        log.info("异步发送字符串{}", JSON.toJSON(message));

        User user1 = new User();
        user1.setEmail("kongquanhj@163.com");
        user1.setName("=============>订单支付");
        user1.setAge(18);
        user1.setCreateTime(LocalDateTime.now());
        RocketMqMessage message1 = RocketMqMessage.builder()
                .topic(rocketmqNameAsyncTopicOrder)
                .tag("async-order")
                .keys("888888888888")
                .message(JSON.toJSONString(user1))
                .messageModule("async-order")
                .build();
        producerMqService.asyncSendOrderly(message1);
        log.info("异步发送字符串{}", JSON.toJSON(message1));
    }

    /**
     * 单向发送
     * 页面访问http://localhost:8088/rocketmq/oneway
     *
     * @throws Exception
     */
    @GetMapping("oneway")
    public void oneway() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-oneway-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameOnewayTopic)
                .tag("oneway")
                .keys("*")
                .message(JSON.toJSONString(user))
                .messageModule("oneway")
                .build();
        producerMqService.sendOneWay(message);
        log.info("单向发送字符串{}", JSON.toJSON(message));
    }

    /**
     * 简单发送
     * 页面访问http://localhost:8088/rocketmq/simple
     *
     * @throws Exception
     */
    @GetMapping("simple")
    public void convertAndSend() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-simple-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder()
                .topic(rocketmqNameSimpleTopic)
                .tag("simple")
                .keys("*")
                .message(JSON.toJSONString(user))
                .messageModule("simple")
                .build();
        producerMqService.convertAndSend(message);
        log.info("简单发送字符串{}", JSON.toJSON(message));
    }

    /**
     * producer发送
     * 页面访问http://localhost:8088/rocketmq/producer
     *
     * @throws Exception
     */
    @GetMapping("producer")
    public void producerSend() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-producer-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder().topic(rocketmqNameProducerTopic)
                .tag("producer")
                .keys("*")
                .producerGroup("kongquan-producer-test-group")
                .message(JSON.toJSONString(user))
                .messageModule("producer")
                .build();
        producerMqService.producerSend(message);
        log.info("producer发送字符串{}", JSON.toJSON(message));
    }

    /**
     * producer发送 死信队列
     * 页面访问http://localhost:8088/rocketmq/dlq
     *
     * @throws Exception
     */
    @GetMapping("dlq")
    public void dlq() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-dlq-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder().topic(rocketmqNameDlqopic)
                .tag("dlq")
                .keys("*")
                .producerGroup("kongquan-dlq-test-group")
                .message(JSON.toJSONString(user))
                .messageModule("dlq")
                .build();
        producerMqService.producerSend(message);
        log.info("producerdlq发送字符串{}", JSON.toJSON(message));
    }

    /**
     * 发送延时队列
     * 页面访问http://localhost:8088/rocketmq/dlq
     *
     * @throws Exception
     */
    @GetMapping("delay")
    public void delay() throws Exception {
        User user = new User();
        user.setEmail("kongquanhj@163.com");
        user.setName("kongquan-dlq-test");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        RocketMqMessage message = RocketMqMessage.builder().topic(rocketmqNameDelayTopic)
                .tag("delay")
                .keys("*")
                .producerGroup("kongquan-delay-test-group")
                .message(JSON.toJSONString(user))
                .messageModule("delay")
                .delayLevel(4)
                .build();
        producerMqService.syncSendDelay(message);
        log.info("producer发送延时消息字符串{}", JSON.toJSON(message));
    }
}
