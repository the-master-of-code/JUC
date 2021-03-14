package com.xyl.juc03;

public class Join01 {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i <5 ; i++) {
            Thread03 thread03 = new Thread03();
            thread03.start();
            thread03.join();
        }

        System.out.println("所有线程都执行完毕");

    }
}
class Thread03 extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"正在执行");
    }
}