package com.formssi.mall.redis.constants;

public class RedisConstant {
    public static final String REDISSON_CONDITION_PREFIX = "mall.redisson";
    public static final String REDISSON_CONDITION_YES = "true";
    public static final String REDISSON_STRATEGY_SINGLE = "single";
    public static final String REDISSON_STRATEGY_CLUSTER = "cluster";
    public static final String REDISSON_STRATEGY_SENTINEL = "sentinel";

    public static final String CACHE_CONDITION_PREFIX = "mall.cache";
    public static final String CACHE_CONDITION_YES = "true";
    public static final String CACHE_STRATEGY_REDIS = "redis";
    public static final String CACHE_STRATEGY_REDISSION = "redisson";
}
