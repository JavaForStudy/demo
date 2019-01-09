package com.example.demo.java8;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/7 13:40
 **/
public class testStream {

    public static void main(String[] args){

        // peek: 生成一个包含原Stream的所有元素的新Stream，
        // 同时会提供一个消费函数（Consumer实例），
        // 新Stream每个元素被消费的时候都会执行给定的消费函数；
        // 可以用于调试
        List<String> collect = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        System.out.println(collect);

    }

}
