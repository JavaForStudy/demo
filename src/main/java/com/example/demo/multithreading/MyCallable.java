package com.example.demo.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 10:39
 **/
public class MyCallable implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        return 123;
    }

    public static void main(String[] args) throws Exception{
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());

    }
}
