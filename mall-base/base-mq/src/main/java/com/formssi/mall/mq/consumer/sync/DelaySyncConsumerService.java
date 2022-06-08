package com.formssi.mall.mq.consumer.sync;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.formssi.mall.mq.Enum.MqLogEnum;
import com.formssi.mall.mq.mapper.MqLogSuccessMapper;
import com.formssi.mall.mq.pojo.MqLogSuccess;
import com.formssi.mall.mq.pojo.User;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 延时消费
 */
@RocketMQMessageListener(topic = "${rocketmq.name.delay.topic}", consumerGroup = "kongquan-delay-sync-test-group")
@Component
public class DelaySyncConsumerService implements RocketMQListener<MessageExt> {

    @Autowired
    private MqLogSuccessMapper mqLogSuccessMapper;

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("TOPIC:kongquan-delay-sync-test 消费者DelaySyncConsumerService收到延时消息：" + JSON.parseObject(messageExt.getBody(), User.class));

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
