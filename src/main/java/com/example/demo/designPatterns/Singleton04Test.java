package com.example.demo.designPatterns;

import java.io.*;
import java.sql.SQLOutput;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/9 16:35
 **/
public class Singleton04Test {

    public static void main(String[] args) throws Exception{

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test"));
        oos.writeObject(Singleton04.getUniqueSingleton());
        oos.close();

        File file = new File("test");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));




        Singleton04 newInstance = (Singleton04) ois.readObject();
        System.out.println(newInstance == Singleton04.getUniqueSingleton());
        file.delete();

    }

}
