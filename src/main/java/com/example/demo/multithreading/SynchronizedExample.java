package com.example.demo.multithreading;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 13:54
 **/
public class SynchronizedExample {

    public void fun1(){
        synchronized (this){
            for (int i = 0; i < 10; i++){
                System.out.println( i + "");
            }
        }
    }

//    public static  void main

}
