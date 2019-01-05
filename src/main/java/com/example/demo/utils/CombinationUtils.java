package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 网上例子 http://cgs1999.iteye.com/blog/2327664
 * 从n个数里取出m个数的排列或组合算法实现
 * 递归算法比较高效
 *
 * @author chengesheng
 * @date 2016年9月28日 下午3:18:34
 */
public class CombinationUtils {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<int[]> combinationResult = combinationSelect(10,3);
        long end = System.currentTimeMillis();

        System.out.println(end - start);

        /*for (int[] ints : combinationResult) {
            System.out.println(Arrays.toString(ints));
        }*/
    }


    /**
     * 组合选择
     * 从 n 个元素中选择 m 个元素来组合
     * @param n
     * @param m
     * @return
     */
    public static List<int[]> combinationSelect(int n, int m) {
        if (n <= 0 || m <= 0 || m > n) {
            throw new RuntimeException(String.format("参数错误: 从 %s 个元素中选取 %s 个元素！", n, m));
        }


        // 要响应的组合结果
        List<int[]> combinationList = new ArrayList<>();

        // 需要进行组合的数据
        int[] dataList = new int[n];
        for(int i=0; i<n;i++){
            dataList[i] = i;
        }
        combinationSelect(dataList, 0, new int[m], 0, combinationList);

        return combinationList;
    }

    /**
     * 组合选择
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void combinationSelect(int[] dataList, int dataIndex, int[] resultList, int resultIndex,List<int[]> combinationList) {

        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;

        // 全部选择完时，输出组合结果
        if (resultCount > resultLen) {
            // System.out.println(Arrays.asList(resultList));
            combinationList.add(Arrays.copyOf(resultList,resultList.length));
            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1, combinationList);
        }

    }


}