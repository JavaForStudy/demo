package com.example.demo.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 16:13
 **/
public class WaitNotifyExample {

    public synchronized void before(){
        System.out.println("before");
        notifyAll();
    }

    public synchronized void after(){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }

    /*
     * 使用 wait() 挂起期间，线程会释放锁。
     * 这是因为，如果没有释放锁，那么其它线程就无法进入对象的同步方法或者同步控制块中，
     * 那么就无法执行 notify() 或者 notifyAll() 来唤醒挂起的线程，造成死锁。
     */
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample example = new WaitNotifyExample();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());
    }

}
