package com.formssi.mall.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chison
 * @date 2022/5/27 11:43
 * @description
 */
@EnableDiscoveryClient
@MapperScan("com.formssi.mall.account.dao")
@SpringBootApplication
public class SeataAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataAccountApplication.class, args);
    }
}
