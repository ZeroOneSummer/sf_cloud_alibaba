package com.formssi.mall.mq.consumer.sync;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.formssi.mall.mq.Enum.MqLogEnum;
import com.formssi.mall.mq.mapper.MqLogSuccessMapper;
import com.formssi.mall.mq.pojo.MqLogSuccess;
import com.formssi.mall.mq.pojo.User;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于注解的监听消息消费者  带顺序消费
 */
@RocketMQMessageListener(topic = "${rocketmq.name.sync.topic.order}", consumerGroup = "kongquan-sync-order-test-group",consumeMode = ConsumeMode.ORDERLY)
@Component
public class SyncOrderConsumerService implements RocketMQListener<MessageExt> {

    @Autowired
    private MqLogSuccessMapper mqLogSuccessMapper;

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("SyncOrderConsumerService收到消息，带顺序消费：" + JSON.parseObject(messageExt.getBody(), User.class));

        try {
            // TODO 处理业务逻辑
            // 修改消息消费状态 成功
            UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("msg_id", messageExt.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_SUCCESS.getMsgStatus());
            mqLogSuccessMapper.update(null,updateWrapper);
        }catch (Exception e){

            // 修改消息消费状态 失败
            UpdateWrapper<MqLogSuccess> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("msg_id", messageExt.getMsgId()).set("msg_status", MqLogEnum.MESS_CONSUMER_FAIL.getMsgStatus());
            mqLogSuccessMapper.update(null,updateWrapper);
        }
    }
}
