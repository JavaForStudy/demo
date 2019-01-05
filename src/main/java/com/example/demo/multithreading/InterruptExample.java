package com.example.demo.multithreading;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 11:43
 **/
public class InterruptExample {

    private static class MyThread1 extends Thread{

        @Override
        public void run(){

            try {
                Thread.sleep(20000);
                System.out.println("MyThread1.run() ===== ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private static  class MyThread2 extends Thread{

        @Override
        public void run(){

            System.out.println("MyThread2 run");
            while (!interrupted()){
                System.out.println("MyThread2 ");
            }
            System.out.println("MyThread2 end");
        }
    }

    public  static void main(String[] agrs){
//        Thread thread = new MyThread1();
//        thread.start();
//        thread.interrupt();
//        System.out.println("Main run");

        Thread thread1 = new MyThread2();
        thread1.start();
        System.out.println(thread1.getName());
        thread1.interrupt();


    }

}
