package com.formssi.mall.sharding.controller;

import com.formssi.mall.sharding.util.IdWorke;
import com.formssi.mall.sharding.util.SnowFlakeUtil;

public class TestSnow {
    public static void main(String[] args) {

        /*1531469396088852480
        1531889551558184960
        1531889593845157888
        1531889612392370176*/
        System.out.println("余数："+ 1531469396088852480L%4);
        System.out.println("余数："+ 1531889551558184960L%4);
        System.out.println("余数："+ 1531889593845157888L%4);
        System.out.println("余数："+ 1531889612392370177L%4);


        for (int i = 0; i < 100; i++) {
            SnowFlakeUtil worker = new SnowFlakeUtil(1, 1, 1);
            IdWorke IdWorke = new IdWorke(1,1);
            Long snowid1 = worker.nextId();
            Long snowid2 = IdWorke.nextId();
            new Thread(()->{
                System.out.println(snowid1+ ":SnowFlakeUtil:");
                System.out.println(snowid1 + ":IdWorke");
                //System.out.println("余数："+ snowid%4);
            }).start();

        }
    }
}
