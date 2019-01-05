package com.example.demo.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 13:41
 **/
public class ExecutorExample {

    private static class MyThread extends Thread{

        @Override
        public void run(){
            System.out.println("MyThread1.run() ===== ");
        }

    }

    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new MyThread());

        FutureTask<?> futureTask = (FutureTask<?>) executorService.submit(new MyThread());
        System.out.println(futureTask);
//        futureTask.cancel(true);

    }

}
