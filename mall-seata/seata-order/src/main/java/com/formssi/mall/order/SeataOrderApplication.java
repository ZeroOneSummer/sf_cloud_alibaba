package com.formssi.mall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chison
 * @date 2022/5/27 11:51
 * @description
 */
@EnableFeignClients(basePackages = "com.formssi.mall.order.feign")
@EnableDiscoveryClient
@MapperScan("com.formssi.mall.order.dao")
@SpringBootApplication
public class SeataOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication.class, args);
    }
}
