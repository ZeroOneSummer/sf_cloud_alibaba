package com.formssi.mall.redis;

import com.formssi.mall.redis.base.BaseTest;
import com.formssi.mall.redis.bean.User;
import com.formssi.mall.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author jp
 * @version 1.0
 * @Description: Redis测试类
 * @date 2022/4/25 11:20
 */
@Slf4j
public class RedisTests extends BaseTest {

    @Autowired(required = false)
    private RedisService redisService;

    @Test
    public void testSet() throws Exception{
        //set
        redisService.set("test_redis_key", "mall_redis！");
        log.info("获取存在的key ->  {}", redisService.get("test_redis_key"));
        log.info("获取不存在的key -> {}", redisService.get("my_key"));

        //set + ttl
        redisService.set("test_redis_ttl_key", "ttl_mall_redis！", 5, TimeUnit.SECONDS);
        log.info("超时前的key -> {}", redisService.get("test_redis_ttl_key"));
        TimeUnit.SECONDS.sleep(6);
        log.info("超时后的key -> {}", redisService.get("test_redis_ttl_key"));

        //object
        User user = new User(1001, "lisa");
        redisService.set("test_user", user);  //{"@class":"com.formssi.mall.redis.bean.User","id":1001,"name":"lisa"}
        Object objUser = redisService.get("test_user");
        log.info("test_user -> {}", objUser);
        User user2 = (User)objUser;
        log.info("id: {}, name: {}", user2.getId(), user2.getName());
    }
}
