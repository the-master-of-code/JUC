package com.xyl.juc01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock02 {
    public static void main(String[] args) {
        Cook cook = new Cook();
        new Thread(()->{ for (int i = 0; i < 10; i++) cook.start();},"A").start();
        new Thread(()->{ for (int i = 0; i < 10; i++) cook.fire();},"B").start();
        new Thread(()->{ for (int i = 0; i < 10; i++) cook.vegetable();},"C").start();
        new Thread(()->{ for (int i = 0; i < 10; i++) cook.out();},"D").start();
    }
}
class Cook {
    private Byte operation = 0;//初始状态0，-->开火；1-->热油；2-->下菜;3-->出锅；
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();//开火的监视器
    private final Condition condition2 = lock.newCondition();//热油的监视器
    private final Condition condition3 = lock.newCondition();//下菜的监视器
    private final Condition condition4 = lock.newCondition();//出锅的监视器

    public void start(){
        lock.lock();//加锁
        try {
            while (operation != 0){
                condition1.await();//开火等待
            }
            operation = 1;//切换状态，指定流程
            System.out.println(Thread.currentThread().getName()+ "----------->开火");
            condition2.signal();//唤醒热油
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }
    public void fire(){
        lock.lock();
        try {
            while (operation != 1){
                condition2.await();//热油等待
            }
            operation = 2;//切换状态，指定流程
            System.out.println(Thread.currentThread().getName()+ "----------->热油");
            condition3.signal();//唤醒下菜
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void vegetable(){
        lock.lock();
        try {
            while (operation != 2){
                condition3.await();//下菜等待
            }
            operation = 3;//切换状态，指定流程
            System.out.println(Thread.currentThread().getName()+ "----------->下菜");
            condition4.signal();//唤醒出锅
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void out(){
        lock.lock();
        try {
            while (operation != 3){
                condition4.await();//出锅等待
            }
            operation = 0;//切换状态，指定流程
            System.out.println(Thread.currentThread().getName()+ "----------->出锅");
            condition1.signal();//唤醒开火
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}