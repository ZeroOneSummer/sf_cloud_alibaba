package com.formssi.mall.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiangyaoting
 * @date 2022/3/28 14:39
 * @description 网关启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ScgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScgApplication.class,args);
    }

}
