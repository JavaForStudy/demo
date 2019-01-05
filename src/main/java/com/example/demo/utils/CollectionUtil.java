package com.example.demo.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by w4206 on 2017/8/23.
 */
public class CollectionUtil {

    /**
     * 网上例子, 获取 List 中重复的元素
     * http://blog.csdn.net/caoxiaohong1005/article/details/54286384
     * @param list
     * @param <E>
     * @return
     */
    public static <E> List<E> getDuplicateElements(List<E> list) {
        return list.stream() // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // 所有 entry 对应的 Stream
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());  // 转化为 List
    }

    /**
     * 取并集
     * @param aList
     * @param bList
     * @param <T>
     * @return
     */
    public static <T> Set<T> union(Collection<T> aList, Collection<T> bList) {
        Set<T> aSet = new HashSet<>(aList != null ? aList : new ArrayList<>());
        Set<T> bSet = new HashSet<>(bList != null ? bList : new ArrayList<>());
        aSet.addAll(bSet);
        return aSet;
    }
    /**
     * 取交集
     * @param aList
     * @param bList
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersect(Collection<T> aList, Collection<T> bList) {
        Set<T> aSet = new HashSet<>(aList != null ? aList : new ArrayList<>());
        Set<T> bSet = new HashSet<>(bList != null ? bList : new ArrayList<>());
        aSet.retainAll(bSet);
        return aSet;
    }


    /**
     * 取多个集合中的交集
     * @param lists
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> Set<T> intersectMore(Collection<T> ... lists) {
        if(lists == null || lists.length == 0){
            return new HashSet<>();
        }

        Set<T> resultList = new HashSet<>(lists[0] != null ? lists[0] : new ArrayList<>());
        for(int i=1; i< lists.length; i++){
            resultList.retainAll(lists[i]);
        }
        return resultList;
    }

    /**
     * 取差集, 注意是第一个集合相对第二个集合的差集
     * @param aList
     * @param bList
     * @param <T>
     * @return
     */
    public static <T> Set<T> diff(Collection<T> aList, Collection<T> bList) {
        Set<T> aSet = new HashSet<>(aList != null ? aList : new ArrayList<>());
        Set<T> intersetctSet = intersect(aList,bList);
        aSet.removeAll(intersetctSet);
        return aSet;
    }


    /**
     * 将 {aa=bb, user_name=10, user_age=20}
     * 转为 {aa=bb, userName=10, userAge=20}
     * @param map
     * @return
     */
    public static Map<String,Object> columnToProperty(Map<String,Object> map){

        if(map == null || map.isEmpty()){
            return new HashMap<>();
        }

        Map<String,Object> newMap = new HashMap<>(map);

        Set<String> keys = map.keySet();

        for (String key : keys) {
            if(!key.contains("_")) continue;
            Object value = newMap.remove(key);
            String newKey = key;

            // 将 _w 转换为 W 之类
            while(newKey.contains("_")){
                String temp = newKey.substring(newKey.indexOf('_'),newKey.indexOf('_') + 2);
                newKey = newKey.replace(temp, temp.toUpperCase().substring(1));
            }

            // 重新 put 进去
            newMap.put(newKey, value);
        }


        return newMap;
    }

    public static List<Integer> arrToList(int[] arr){
        List<Integer> list = new ArrayList<>(arr.length);
        for(int a : arr){
            list.add(a);
        }
        return list;
    }

}
