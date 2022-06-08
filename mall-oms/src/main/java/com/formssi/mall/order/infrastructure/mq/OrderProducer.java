package com.formssi.mall.order.infrastructure.mq;

import com.formssi.mall.order.domain.vo.OrderConsumerVo;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderProducer {

    @Value("${rocketmq.oms-order-topic}")
    private String omsOrderTopic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(String userId,String loginType){
        OrderConsumerVo orderConsumerVo = OrderConsumerVo.builder().userId(userId).loginType(loginType).build();
        SendResult sendResult = rocketMQTemplate.syncSend(omsOrderTopic, orderConsumerVo);
        Optional.ofNullable(sendResult).map(SendResult::getSendStatus).filter(val-> SendStatus.SEND_OK.equals(val)).orElseThrow(()->new IllegalStateException("订单下载消费异常"));
    }
}
