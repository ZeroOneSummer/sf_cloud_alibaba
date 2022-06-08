package com.formssi.mall.redis.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lk
 * @version 1.0
 * @Description: 全局常量枚举 用来拼接完整的URL
 * @date 2022/4/12 13:51
 */
@Getter
@AllArgsConstructor
public enum RedissonEnum {

    //前缀
    URL_PREFIX("redis://", "Redis地址配置前缀"),
    SSL_URL_PREFIX("rediss://", "Redis地址配置SSL前缀"),

    //策略
    SINGLE_STRATEGY("SINGLE_STRATEGY", "单机模式"),
    CLUSTER_STRATEGY("CLUSTER_STRATEGY", "集群模式"),
    SENTINE_STRATEGY("SENTINE_STRATEGY", "哨兵模式");
    ;

    private final String value;
    private final String desc;
}
