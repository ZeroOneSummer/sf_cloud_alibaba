package com.formssi.mall;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RenentrantReadWriteLockEx {

    public static void main(String [] args) throws Exception{
        MyCacheLock myCacheLock = new MyCacheLock();
        new Thread(() -> myCacheLock.read()).start();
        new Thread(()->myCacheLock.read()).start();
        new Thread(() -> myCacheLock.write()).start();
        new Thread(()->myCacheLock.write()).start();
    }

}

class MyCacheLock {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void read(){

        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "开始读取");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "读取ok");
        }
        catch (Exception e){

        }
        finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void write() {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始写入");
            TimeUnit.SECONDS.sleep(5);  //sleep时线程处于TIMED_WAITING状态
            System.out.println(Thread.currentThread().getName() + "写入ok");
        } catch (Exception e) {

        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
