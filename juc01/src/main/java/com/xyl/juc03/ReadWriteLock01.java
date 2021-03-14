package com.xyl.juc03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


//独占锁
//共享锁

//读写锁
//写写互斥
//读写互斥
//读读共享
public class ReadWriteLock01 {
    public static void main(String[] args) {
        Collection01 collection01 = new Collection01();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(()->{
                collection01.write(temp+"",temp);
            },String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(()->{
                collection01.read(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

class Collection01 {
    Map map = new HashMap();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(String s,Object o){
        readWriteLock.writeLock().lock();//独占锁
        try{
            System.out.println(Thread.currentThread().getName()+"开始写入");
            TimeUnit.SECONDS.sleep(1);
            map.put(s,o);
            System.out.println(Thread.currentThread().getName()+"写入完毕");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void read(String s){
        readWriteLock.readLock().lock();//共享锁
        try{
            System.out.println(Thread.currentThread().getName()+"开始读取");
            TimeUnit.SECONDS.sleep(1);
            map.get(s);
            System.out.println(Thread.currentThread().getName()+"读取完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}