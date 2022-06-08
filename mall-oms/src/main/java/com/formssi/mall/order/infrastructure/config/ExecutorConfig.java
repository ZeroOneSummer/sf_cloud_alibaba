package com.formssi.mall.order.infrastructure.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorConfig {

    /**
     * OMS订单线程池
     */
    public static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            new ThreadPoolExecutor(
                    50,
                    100,
                    60L,
                    TimeUnit.SECONDS,new LinkedBlockingQueue(20000),
                    new DefaultThreadFactory("omsOrder"),
                    new ThreadPoolExecutor.CallerRunsPolicy()));
}
