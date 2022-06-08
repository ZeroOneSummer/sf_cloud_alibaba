package com.formssi.mall.redis;

import com.formssi.mall.redis.annotation.DistributedLock;
import com.formssi.mall.redis.base.BaseTest;
import com.formssi.mall.redis.bean.User;
import com.formssi.mall.redis.service.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jp
 * @version 1.0
 * @Description: Redisson测试类
 * @date 2022/4/25 15:40
 */
@Slf4j
public class RedissonTests extends BaseTest {

    @Autowired(required = false)
    private RedissonLockService redissonLockService;

    private int goodTotal = 100;
    private final String LOCK_KEY = "test_redisson_lock";

    /**
     * 手动加锁
     */
    @Test
    public void testLock() throws Exception{
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while (true){
                    try {
                        boolean isLock = redissonLockService.tryLock(LOCK_KEY, 10);
                        if (isLock){
                            if (goodTotal > 0){
                                goodTotal-=1;
                                log.info("{}抢购成功, 库存剩余: {}", Thread.currentThread().getName(), goodTotal);
                            }else {
                                log.info("库存不足!");
                                break;
                            }
                        }else{
                            log.info("尝试获取锁失败!");
                            throw new RuntimeException("尝试获取锁失败!");
                        }
                    }finally {
                        redissonLockService.unlock(LOCK_KEY);
                    }
                }
            }, Thread.currentThread().getName() + "-" + i).start();
        }
    }

    /**
     * 注解-自动加锁
     */
    @Test
    public void testAnnoLock() throws Exception{
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while (true){
                    if(!getGoods()){
                        break;
                    };
                }
            }, Thread.currentThread().getName() + "-" + i).start();
        }
    }

    @DistributedLock(LOCK_KEY)
    boolean getGoods(){
        if (goodTotal > 0){
            goodTotal-=1;
            log.info("{}抢购成功, 库存剩余: {}", Thread.currentThread().getName(), goodTotal);
            return true;
        }else {
            log.info("库存不足!");
            return false;
        }
    }

    @Test
    public void testBucket() throws Exception{
        String userKey = "bucket_user_key";
        //设值
        redissonLockService.setBucket(userKey, new User(2022, "路飞"), 5*60L); //秒
        //取值
        User user = redissonLockService.getBucket(userKey, User.class);
        log.info(user.getName());
        //是否存在
        boolean isExist = redissonLockService.existsBucket(userKey);
        log.info(Boolean.toString(isExist));
        //删除
        redissonLockService.removeBucket(userKey);
    }

    final String topic = "redisson_topic_test";
    @Test
    public void testPush() throws Exception{
        //发布
        long rs = redissonLockService.publishMessage(topic, new User(2022, "索隆"));
        redissonLockService.publishMessage(topic, new User(2023, "乔巴"));
        log.info(Long.toString(rs));
    }
    @Test
    public void testSubscribe() throws Exception{
        //订阅
        redissonLockService.receiveMessage(topic, User.class, (charSequence, msg) -> {
            log.info("主题：{}，消息：{}", charSequence, msg);
        });
    }
    @Test
    public void test() throws Exception{
        //先执行消费者，再执行生产者
        testSubscribe();
        testPush();
    }
}
