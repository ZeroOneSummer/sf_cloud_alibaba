package com.formssi.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<String>{

    private int ticket = 5;

    @Override
    public String call() throws Exception {

        for (int i = 0; i < 100; i++){
            if(this.ticket>0){
                System.out.println("卖票，ticket = " + this.ticket--);
            }
        }

        return "票已卖光";
    }
}

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableDiscoveryClient
public class App {
    public static void main( String[] args ) {
     /*   MyThread th1 = new MyThread();
        MyThread th2 = new MyThread();
        FutureTask<String> task1 = new FutureTask<>(th1);
        FutureTask<String> task2 = new FutureTask<>(th2);
        new Thread(task1).start();
        new Thread(task2).start();

        try{
            System.out.println("A线程返回结果："+task1.get());
            System.out.println("B线程返回结果："+task2.get());
        }
        catch (Exception e){

        }*/

        SpringApplication.run(App.class,args);

    }
}
