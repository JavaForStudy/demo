package com.example.demo.multiThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/10 20:43
 **/
public class ReentrantLockTest {

    static public class Myservice{

        private Lock lock = new ReentrantLock();

        public void testMethod(){
            lock.lock();

            try {
                for (int i = 0; i < 5; i++){
                    System.out.println("TheadName" + Thread.currentThread().getName() + "("+(i+1)+")");
                }
            } finally {
                lock.unlock();
            }
        }

        static public class MyThread extends Thread {
            private Myservice myservice;

            public MyThread(Myservice myservice){
                super();
                this.myservice = myservice;
            }
            public void run() {
                myservice.testMethod();
            }

        }

    }
}
