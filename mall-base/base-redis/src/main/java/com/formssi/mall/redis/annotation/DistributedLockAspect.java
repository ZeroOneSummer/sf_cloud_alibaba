package com.formssi.mall.redis.annotation;

import com.formssi.mall.redis.service.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lk
 * @version 1.0
 * @date 2022/4/12 14:00
 * @description: Redisson分布式锁注解AOP
 */
@Slf4j
@Aspect
@Component
public class DistributedLockAspect {

    @Autowired(required = false)
    RedissonLockService redissonLockService;

    @Around("@annotation(distributedLock)")
    public void around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {

        log.info("分布式锁-lock");
        //获取锁名称
        String lockName = distributedLock.value();
        //获取超时时间，默认10秒
        int leaseTime = distributedLock.leaseTime();
        boolean isLock = redissonLockService.tryLock(lockName, leaseTime);
        try {
            if (isLock){
                log.info("加锁成功，开始执行业务...");
                joinPoint.proceed();
                log.info("业务执行完成.");
            }else {
                log.info("尝试获取锁失败, 稍后重试!");
                TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (Throwable throwable) {
            log.error("加锁失败: ", throwable);
            throwable.printStackTrace();
        } finally {
            //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
            if (redissonLockService.isHeldByCurrentThread(lockName)) {
                redissonLockService.unlock(lockName);
            }
        }
        log.info("分布式锁-unlock");
    }
}
