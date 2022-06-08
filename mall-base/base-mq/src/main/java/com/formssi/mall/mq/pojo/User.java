package com.formssi.mall.mq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用于测试消息发送 消费实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private int version;

    private Integer deleted;


}

