package com.formssi.mall.mq.config;

import com.formssi.mall.mq.mapper.MqLogFailMapper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.formssi.mall.mq.mapper")
public class MapperConfig {

}
