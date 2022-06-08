package com.formssi.mall.mq.consumer.threadpool;

import com.alibaba.fastjson.JSON;

/**
 * 处理积压消息的线程
 */
public class MyWork implements Runnable {
    private Object message;

    public MyWork(Object message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(JSON.toJSONString(message));
    }
}
