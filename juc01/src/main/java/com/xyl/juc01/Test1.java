package com.xyl.juc01;


public class Test1 {
    public static void main(String[] args) {
        //得到此电脑允许同时的最大并行数（也就是cpu逻辑处理器的个数）
        //CPU密集型，IO密集型
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
