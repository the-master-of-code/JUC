package com.xyl.juc01;

import java.sql.Time;
import java.util.concurrent.TimeUnit;


//1.上面这个是自己总结的，真不确定对错，但是看运行结果也有一定道理，因为notifyAll之后，通知的线程消费者等待后生产者直接进入等待完毕状态，跳过了被通知阶段
//notifyAll会直接通知所有可以获得锁的线程，由cpu调度，某个线程获得锁，若该线程不满足执行条件，线程等待，然后直接由cpu再从候选线程里挑出一个去判断执行条件
//notify只会随机通知一个线程，然后该线程判断执行条件，要么等待wait释放锁，被其他线程获得，要么执行后通知其他线程

//2下面这个百度来的，jvm有关，后续在做学习
//如果是通过notify来唤起的线程，那先进入wait的线程会先被唤起来
//如果是通过nootifyAll唤起的线程，默认情况是最后进入的会先被唤起来，即LIFO的策略

public class Synchronized01 {
    public static void main(String[] args) {
        Production02 production02 = new Production02();
        new Thread(()->{ for (int i = 0; i < 9; i++) production02.put(); },"A").start();
        new Thread(()->{ for (int i = 0; i < 2; i++) production02.get(); }, "B").start();
        new Thread(()->{ for (int i = 0; i < 2; i++) production02.get(); }, "C").start();
        new Thread(()->{ for (int i = 0; i < 2; i++) production02.get(); }, "D").start();
        new Thread(()->{ for (int i = 0; i < 2; i++) production02.get(); }, "E").start();
        new Thread(()->{ for (int i = 0; i < 2; i++) production02.get(); }, "F").start();
    }
}
class Production02 {
    final int capacity = 1;//商品数量到达仓库容量后，不允许生产者生产
    final int threshold = 0;//商品数量到达阈值后，不允许消费者消费
    int prod_num = 1;//初始产品数量


    public synchronized void put(){
        System.out.println(Thread.currentThread().getName()+"------>生产者被通知");
        while(prod_num == capacity){
            try {
                System.out.println(Thread.currentThread().getName()+"------>生产者等待,wait会释放锁");
                this.wait();
                System.out.println(Thread.currentThread().getName()+"------>生产者等待完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"------>生产者产生产品");
        prod_num++;
        System.out.println(Thread.currentThread().getName()+"------>仓库里的货物还剩"+prod_num);
        System.out.println(Thread.currentThread().getName()+"------>通知消费者");
        this.notify();
//        this.notifyAll();
    }
    public synchronized void get(){
                System.out.println(Thread.currentThread().getName()+"------>消费者被通知");
        while(prod_num == threshold){
            try {
                System.out.println(Thread.currentThread().getName()+"------>消费者等待，wait会释放锁");
                this.wait();
                System.out.println(Thread.currentThread().getName()+"------>等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"------>获得产品");
        prod_num--;
        System.out.println(Thread.currentThread().getName()+"------>仓库里的货物还剩"+prod_num);
        System.out.println(Thread.currentThread().getName()+"------>通知生产者");
        this.notify();
//        this.notifyAll();
    }
}