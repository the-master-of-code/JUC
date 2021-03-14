package com.xyl.juc03;

import java.util.concurrent.CountDownLatch;

public class CountDown02 {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread02(countDownLatch).start();

        }

        countDownLatch.await();

        System.out.println("所有线程都已执行");
    }
}
class Thread02 extends Thread{
    private CountDownLatch countDownLatch;

    public Thread02(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"正在执行");
        countDownLatch.countDown();
    }
}