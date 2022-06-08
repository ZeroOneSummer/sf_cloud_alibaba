package com.formssi.mall.goods.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步调用线程池
 * @author:prms
 * @create: 2022-04-19 14:45
 */
@Configuration
public class ThreadPoolConfig {

    private static AtomicInteger serialNum = new AtomicInteger(0);

    @Bean("gms-thread-pool")
    public Executor setThreadPoolExecutor() {

        //cpu核心數
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                processors,
                processors * 2,
                30,//空闲线程存活时间
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(50000),//阻塞队列大小暂设50000
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("gms-thread-"+serialNum.decrementAndGet());
                    return thread;
                }
        );
        //拒绝任务由调用者处理
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }

}
