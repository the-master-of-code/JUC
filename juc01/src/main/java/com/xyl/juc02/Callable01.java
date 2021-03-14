package com.xyl.juc02;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//Callable接口的实现类不能直接交给线程启动，一个是run（），一个是call（），他们都不是同一个东西给
//FutureTask类它的构造器参数既可以是Callable，也可以是Runnable加上一个返回值
//所以可以通过FutureTask把Callable的实现类交给线程执行

//FutureTask 实现的是 RunnableFuture 接口
//而RunnableFuture接口里的方法就一个  是run()

//FutureTask 源码分析
//有一个变量 outcome
//有run()方法，把传入的参数Callable接口的实现类的run()的返回值赋给了 outcome
//有一个get()方法，调用report()方法，返回了outcome的值

//可以通过FutureTask的方法get得到Callable实现类的返回值

//当然，Callable的实现类也可以交给线程池来 提交执行
public class Callable01 {
    public static void main(String[] args) {
        My my = new My();
        FutureTask  futureTask = new FutureTask<>(my);//适配类

        new Thread(futureTask,"A").start();

        new Thread(futureTask,"B").start();//结果会被缓存，可以提高效率          想要获得他的值，应该只有用线程池才能拿到了

        String result = null;
        try {
            result = (String)futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);



    }
}

class My implements Callable<String>{
    @Override
    public String call() {
        String result = Thread.currentThread().getName();
        return result;
    }
}