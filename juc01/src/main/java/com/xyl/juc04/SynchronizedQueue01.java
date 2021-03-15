package com.xyl.juc04;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;


//SynchronousQueue的容量为0，存进去一个数必须得等到取出来后才能继续往里面存数据
public class SynchronizedQueue01 {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    System.out.println(Thread.currentThread().getName()+"put--------->"+i);
                    synchronousQueue.put(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+"get--------->"+synchronousQueue.take());
//                    synchronousQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }
}
