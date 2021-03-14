package com.xyl.juc01;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Test1 {
    public static void main(String[] args) {
        //得到此电脑允许同时的最大并行数（也就是cpu逻辑处理器的个数）
        //CPU密集型，IO密集型
        System.out.println(Runtime.getRuntime().availableProcessors());



        List<String> list = Arrays.asList("a","b","c");
        list.forEach(System.out::println);



    }
}
