package com.formssi.mall.mq.producer;


import com.formssi.mall.mq.pojo.RocketMqMessage;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;

public interface ProducerMqService {
    /**
     * 同步发送
     * @param message
     * @return
     * @throws Exception
     */
    SendResult syncSend(RocketMqMessage message) throws Exception;

    /**
     * 同步发送 带延时
     * @param message
     * @return
     * @throws Exception
     */
    SendResult syncSendDelay(RocketMqMessage message) throws Exception;

    /**
     * 同步发送带顺序
     * @param message
     * @return
     * @throws Exception
     */
    SendResult syncSendOrderly(RocketMqMessage message) throws Exception;

    /**
     * 异步发送
     * @param message
     * @throws Exception
     */
    void asyncSend(RocketMqMessage message) throws Exception;

    /**
     * 异步发送 带顺序
     * @param message
     * @throws Exception
     */
    void asyncSendOrderly(RocketMqMessage message) throws Exception;

    /**
     * 单向发送
     * @param message
     * @throws Exception
     */
    void sendOneWay(RocketMqMessage message) throws Exception;

    /**
     * 简单发送
     * @param message
     * @throws Exception
     */
    void convertAndSend(RocketMqMessage message) throws Exception;

    /**
     * 自定义发送
     * @param message
     * @return
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     */
    boolean producerSend(RocketMqMessage message) throws MQClientException, RemotingException, InterruptedException;


}
