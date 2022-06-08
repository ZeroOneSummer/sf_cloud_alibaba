package com.formssi.mall.mq;

import com.formssi.mall.common.enums.MessageType;
import com.formssi.mall.common.util.JsonUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerService {
    public String sendMessage(News news) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = null;
        try {
            producer = new DefaultMQProducer("demo_producer_group");
            producer.setNamesrvAddr("10.207.0.164:9876");
            producer.start();
            Message mess = new Message("boot2", news.getMessageType().toString(), "keys", JsonUtils.toJSON(news).getBytes());
            producer.send(mess);
        }  catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        } finally {
            producer.shutdown();
        }
            return "发送成功";
    }
    @Test
    public void test() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<String> list = new ArrayList<>();
        list.add("1122334455@qq.com");
        List<String> imgs = new ArrayList<>();
        imgs.add("text");
        System.out.println(sendMessage(new News(list,"消息主题","邮件内容",imgs,MessageType.Advance_TYPE)));
    }
}