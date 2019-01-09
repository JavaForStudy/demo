package com.example.demo.classLoader;

import com.example.demo.common.DataBean;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/8 14:48
 **/
public class loaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = DataBean.class.getClassLoader();
        System.out.println(loader);
//        使用ClassLoader.loadClass()来加载类，不会执行初始化块
//        loader.loadClass("com.example.demo.classLoader.Test2");
//        使用Class.forName()来加载类，默认会执行初始化块
//         Class.forName("com.example.demo.classLoader.Test2");
//        使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("com.example.demo.classLoader.Test2", false, loader);
    }

}
