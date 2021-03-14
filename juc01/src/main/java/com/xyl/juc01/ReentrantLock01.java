package com.xyl.juc01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock01 {
    public static void main(String[] args) {
        Production production = new Production();
        new Thread(()->{ for (int i = 0; i < 5; i++) production.get();},"A").start();
        new Thread(()->{ for (int i = 0; i < 5; i++) production.get();},"B").start();
        new Thread(()->{ for (int i = 0; i < 5; i++) production.put();},"C").start();
        new Thread(()->{ for (int i = 0; i < 5; i++) production.put();},"D").start();
    }
}
//产品
class Production {
    private int num = 0;//数量
    private final Lock lock = new ReentrantLock();
//    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    public  void put() {
        lock.lock();try{
            while(num != 0 ){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+ "------------->"+num);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public  void get() {
        lock.lock();try{
            while(num == 0 ){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+ "------------->"+num);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}