package com.formssi.mall;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); //获得，如果已满，就等待
                    System.out.println(Thread.currentThread().getName() + "停车");
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "离开");
                } catch (InterruptedException e) {

                } finally {
                    semaphore.release(); //释放，会将当前型号量释放+1，然后唤醒等待的线程
                }
            }, String.valueOf(i)).start();
        }
    }
}
