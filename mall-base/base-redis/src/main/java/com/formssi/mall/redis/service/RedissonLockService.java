package com.formssi.mall.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author lk & jp
 * @version 1.0
 * @date 2022/4/12 13:51
 * @description: Redisson通用方法
 */
@Slf4j
@ConditionalOnBean(Redisson.class)
public class RedissonLockService {

    @Autowired
    private Redisson redisson;

    /**
     * 加锁操作 （设置锁的有效时间）
     *
     * @param lockName   锁名称
     * @param expireTime 锁有效时间
     */
    public void lock(String lockName, long expireTime) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(expireTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁操作 (锁有效时间采用默认时间30秒）
     *
     * @param lockName 锁名称
     */
    public void lock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        //默认看门狗3*10s续期，中间完成任务不续期
        rLock.lock();
    }

    /**
     * 加锁操作(tryLock锁，没有等待时间）
     *
     * @param lockName  锁名称
     * @param expireTime 锁有效时间
     */
    public boolean tryLock(String lockName, long expireTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(expireTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    /**
     * 加锁操作(tryLock锁，有等待时间）
     *
     * @param lockName  锁名称
     * @param leaseTime 锁有效时间
     * @param waitTime  等待时间
     */
    public boolean tryLock(String lockName, long leaseTime, long waitTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    /**
     * 解锁
     *
     * @param lockName 锁名称
     */
    public void unlock(String lockName) {
        redisson.getLock(lockName).unlock();
    }

    /**
     * 判断该锁是否已经被线程持有
     *
     * @param lockName 锁名称
     */
    public boolean isLock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isLocked();
    }


    /**
     * 判断该线程是否持有当前锁
     *
     * @param lockName 锁名称
     */
    public boolean isHeldByCurrentThread(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isHeldByCurrentThread();
    }

    /**
     * 存储缓存
     */
    public <T> void setBucket(String key, T value) {
        RBucket<T> bucket = redisson.getBucket(key, StringCodec.INSTANCE);
        bucket.set(value);
    }

    /**
     * 存储过期缓存
     */
    public <T> void setBucket(String key, T value, long expired) {
        RBucket<T> bucket = redisson.getBucket(key, JsonJacksonCodec.INSTANCE);
        bucket.set(value, expired, TimeUnit.SECONDS);
    }

    /**
     * 读取缓存
     */
    public <T> T getBucket(String key, Class<T> clazz) {
        RBucket<T> bucket = redisson.getBucket(key, JsonJacksonCodec.INSTANCE);
        return bucket.get();
    }

    /**
     * 移除缓存
     */
    public void removeBucket(String key) {
        redisson.getBucket(key).delete();
    }

    /**
     * 判断缓存是否存在
     */
    public boolean existsBucket(String key) {
        return redisson.getBucket(key).isExists();
    }

    /**
     * 发布消息
     */
    public <T> long publishMessage(String topic, T message) {
        //T要实现序列化接口
        RTopic rTopic = redisson.getTopic(topic, new SerializationCodec());
        long l = rTopic.publish(message);
        log.info("message push success.");
        return l;
    }

    /**
     * 订阅消息
     */
    public <T> void receiveMessage(String topic, Class<T> clazz, BiConsumer<? super CharSequence, T> consumer) {
        RTopic rTopic = redisson.getTopic(topic, new SerializationCodec());
        rTopic.addListenerAsync(clazz, (charSequence, msg) -> {
                    //消费的具体内容
                    consumer.accept(charSequence, msg);
                    log.info("message consumer complete.");
                }
        );
    }

}
