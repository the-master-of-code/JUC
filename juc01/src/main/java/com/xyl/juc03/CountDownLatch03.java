package com.xyl.juc03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


//join()不适用但countDownLatch适用的场景
//worker的工作可以分为两个阶段，work2 只需要等待work0和work1完成他们各自工作的第一个阶段之后就可以开始自己的工作了，而不是必须等待work0和work1把他们的工作全部完成之后才能开始

public class CountDownLatch03 {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) new Thread04(countDownLatch).start();

        countDownLatch.await();

        System.out.println("主线程可以开始工作");

    }
}

class Thread04 extends Thread{
    private CountDownLatch countDownLatch;

    public Thread04(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"第一阶段工作完成");



        countDownLatch.countDown();//可以使主线程在从线程第一阶段任务完成后就开始工作


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"第二阶段工作完成");
    }
}