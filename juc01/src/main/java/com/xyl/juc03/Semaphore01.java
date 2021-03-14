package com.xyl.juc03;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//用来限流，只有几个线程能进去
public class Semaphore01 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 9; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();//得到许可
                    System.out.println(Thread.currentThread().getName()+"抢到了车位");

                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName()+"离开了车位");
                    semaphore.release();//释放信号量
                }




            },String.valueOf(i)).start();
        }


    }
}
