package com.example.demo.designPatterns;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/3 17:18
 **/
public class Singleton {

    private static Singleton instance;
    private Singleton (){}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
