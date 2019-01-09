package com.example.demo.designPatterns;

import java.io.Serializable;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/9 16:35
 **/
public class Singleton04 implements Serializable {

    private static volatile  Singleton04 uniqueSingleton;

    private Singleton04(){

    }

    public static Singleton04 getUniqueSingleton(){

        if (uniqueSingleton == null){
            synchronized (Singleton04.class){
                if (uniqueSingleton==null) uniqueSingleton = new Singleton04();
            }
        }
        return uniqueSingleton;
    }

}
