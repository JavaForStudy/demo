package com.example.demo.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by mairongxin on 2016/11/9.
 */
public class ListUtil {
    private static <T> T getByIndexWithDefault(List<T> list, int index, T defaultObj) {
        return getByIndex(list, index).orElse(defaultObj);
    }

    private static <T> Optional<T> getByIndex(List<T> list, int index) {
        return Optional.ofNullable(list)
                .filter(list1 -> list1.size() > index)
                .map(list1 -> list1.get(index));
    }

    public static <T> T getFirstWithDefault(List<T> list, T defaultObj) {
        return getByIndexWithDefault(list, 0, defaultObj);
    }

    public static <T> Optional<T> getFirst(List<T> list) {
        return getByIndex(list, 0);
    }

    public static <T> List<T> getPage(List<T> all, Integer offset, Integer limit) {
        List<T> list = new ArrayList<>();
        if (all == null) {
            return list;
        }
        Integer total = all.size();
        if (offset == null || offset < 0) {
            offset = 0;
        }
        if (limit == null) {
            limit = 0;
        }
        if (total > offset) {//避免位移超出界限
            int fromIndex = offset;
            int toIndex = fromIndex + limit;
            if (toIndex >= total) {//避免范围超出界限
                toIndex = total;
            }
            return all.subList(fromIndex, toIndex);
        }
        return list;
    }

    public static <T> void handlePage(List<T> all, Integer pageSize, Consumer<List<T>> consumer) {
        Objects.requireNonNull(all);
        Objects.requireNonNull(pageSize);
        Objects.requireNonNull(consumer);
        for (int offset = 0; offset < all.size(); offset += pageSize) {
            List<T> page = getPage(all, offset, pageSize);
            if (page != null && !page.isEmpty()) {
                consumer.accept(page);
            }
        }
    }

    public static <T, R> List<R> getPropertyList(List<T> list, Function<? super T, ? extends R> mapper) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 将list以某个属性为key,转成Map 。如果list里面有多个对象这个属性都一样的话，只返回第一个 <br/> <br/>
     * List<StoreGoods> list = new ArrayList<>(); <br/>
     * StoreGoods s = new StoreGoods(); <br/>
     * s.setGoodsId(1L); <br/>
     * <p>
     * Map<Long,StoreGoods> goodsMap = ListUtil.toMap(list, StoreGoods::getGoodsId); <br/>
     * 结果 ：<1L,s> <br/>
     *
     * @param list
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> toMap(List<T> list, Function<? super T, ? extends R> mapper) {
        if (list == null) {
            return new HashMap<>();
        }
        Objects.requireNonNull(mapper);
        return list.stream().collect(Collectors.toMap(mapper, Function.identity(), (o, o2) -> o));
    }

    /**
     * 将list以某个属性为key,某个属性为value,转成Map 。如果list里面有多个对象这个属性都一样的话，只返回第一个 <br/> <br/>
     * List<StoreGoods> list = new ArrayList<>(); <br/>
     * StoreGoods s = new StoreGoods(); <br/>
     * s.setGoodsId(1L); <br/>
     * s.setTitle("商品名称"); <br/>
     * <p>
     * Map<Long,String> goodsMap = ListUtil.toMap(list, StoreGoods::getGoodsId,StoreGoods::getTitle); <br/>
     * 结果 ：<1L,"商品名称"> <br/>
     *
     * @param list
     * @return
     */
    public static <T, K, U> Map<K, U> toMap(List<T> list, Function<? super T, ? extends K> kMapper, Function<? super T, ? extends U> vMapper) {
        if (list == null) {
            return new HashMap<>();
        }
        Objects.requireNonNull(kMapper);
        Objects.requireNonNull(vMapper);
        return list.stream().collect(Collectors.toMap(kMapper, vMapper, (o, o2) -> o));
    }

    /**
     * 根据list里面对象的某个属性进行分组
     *
     * @param list
     * @param kMapper
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Map<K, List<T>> groupingBy(List<T> list, Function<? super T, ? extends K> kMapper) {
        if (list == null) {
            return new HashMap<>();
        }
        Objects.requireNonNull(kMapper);
        return list.stream().collect(Collectors.groupingBy(kMapper));
    }


    public static <T> List<T> filter(List<T> list, Predicate<? super T> filter) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().filter(filter).collect(Collectors.toList());
    }

    public static <T> List<T> distinct(List<T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static <T> List<T> sorted(List<T> list, Comparator<? super T> comparator) {
        if (list == null) {
            return new ArrayList<>();
        }
        Objects.requireNonNull(comparator);
        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static List<String> asList(String[] items) {
        return items == null ? new ArrayList<>() : Arrays.asList(items);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(1000);
        for (int i = 0; i < 105; i++) {
            list.add(String.valueOf(i));
        }
        handlePage(list, 10, System.out::println);
    }

    /**
     * 以一定的数量进行分组
     *
     * @param list
     * @param number
     * @param <T>
     * @return
     */
    private static <T> List<List<T>> spilt(List<T> list, int number) {
        List<List<T>> lists = new ArrayList<>();
        if (list != null) {
            int size = list.size();
            int fromIndex = 0;
            while (fromIndex < size) {
                int toIndex = fromIndex + number;
                toIndex = toIndex > size ? size : toIndex;
                List<T> subList = list.subList(fromIndex, toIndex);
                lists.add(subList);
                fromIndex = toIndex;
            }
        }
        return lists;
    }

    /**
     * 判断是否为空，null也算空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean equals(List<T> list1, List<T> list2) {
        Objects.requireNonNull(list1);
        Objects.requireNonNull(list2);
        return list1.containsAll(list2) && list2.containsAll(list1);
    }
}
