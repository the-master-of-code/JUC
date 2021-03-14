package com.xyl.juc03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class CountDown01 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);


        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"-------->线程执行");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }


        countDownLatch.await();

        System.out.println("所有线程执行完毕");

    }
}
