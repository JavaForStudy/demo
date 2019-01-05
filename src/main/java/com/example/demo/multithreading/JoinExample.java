package com.example.demo.multithreading;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/4 15:33
 **/
public class JoinExample {

    private class A extends Thread {
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    private class B extends Thread {

        private A a;

        B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        }
    }

    /*
     *  线程协作
     *      b线程先启动 -> b挂起 、等待a线程执行完毕 后b启动
     */
    public void test() {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();//如果a没有调用start()方法 a线程join过来 也是不会执行的
    }

    public static void main(String[] args) {
        JoinExample example = new JoinExample();
        example.test();
    }

}