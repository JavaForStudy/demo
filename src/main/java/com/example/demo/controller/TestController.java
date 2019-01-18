package com.example.demo.controller;

import com.example.demo.common.DataBean;
import com.example.demo.redis.redLock.AquiredLockWorker;
import com.example.demo.redis.redLock.RedisLocker;
import com.example.demo.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/9 13:48
 **/
@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("/test1")
    public DataBean test() {
        return new DataBean(ServiceManager.testService.testFun());
    }


    @Autowired
    private RedisLocker distributedLocker;

    /**
     * 测试分布式锁RedLock
     * @return
     * @throws Exception
     */
    @RequestMapping("/testRedLock")
    public String testRedLock() throws Exception {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) { // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        startSignal.countDown(); // let all threads proceed
        doneSignal.await();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }

    class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
                startSignal.await();
                distributedLocker.lock("test", new AquiredLockWorker<Object>() {

                    @Override
                    public Object invokeAfterLockAquire() {
                        doTask();
                        return null;
                    }

                });
            } catch (Exception e) {

            }
        }

        void doTask() {
            System.out.println(Thread.currentThread().getName() + " start");
            Random random = new Random();
            int _int = random.nextInt(200);
            System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
            try {
                Thread.sleep(_int);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
            doneSignal.countDown();
        }


    }

}