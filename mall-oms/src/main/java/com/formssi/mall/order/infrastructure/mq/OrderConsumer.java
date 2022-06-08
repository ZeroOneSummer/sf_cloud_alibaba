package com.formssi.mall.order.infrastructure.mq;

import com.formssi.mall.order.domain.vo.OrderConsumerVo;
import com.formssi.mall.order.infrastructure.config.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配置文件参考：RocketMQProperties
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.oms-order-topic}",consumerGroup = "${rocketmq.consumer-group}")
public class OrderConsumer implements RocketMQListener<OrderConsumerVo> {

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(OrderConsumerVo message) {
        log.info("OrderConsumer.onMessage:{}",message);

        //下载逻辑
        System.out.println("订单下载成功");
        //拿到用户ID和客户端类型通知前端
        webSocketServer.send(message.getUserId(),message.getLoginType(),()->"订单下载完成",false);
    }
}
