package com.formssi.mall;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("在启动 barrier 时执行的命令；如果不执行任何操作，则该参数为 null");
        });
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "get out");
                try {
                    cyclicBarrier.await(); //在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
                    System.out.println("等所有人都出来了一起回家");
                    System.out.println(cyclicBarrier.getParties());//返回要求启动此 barrier 的参与者数目
                } catch (InterruptedException e) {

                } catch (BrokenBarrierException e) {

                }
            }, String.valueOf(i)).start();
        }
    }
}
