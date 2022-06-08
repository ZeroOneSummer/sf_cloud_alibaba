package com.formssi.mall.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.formssi.mall.mq.mapper")
public class BaseMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseMqApplication.class, args);
    }


}
