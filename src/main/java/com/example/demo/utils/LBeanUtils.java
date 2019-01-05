package com.example.demo.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.*;

/**
 * Created by oyhk on 15/11/5.
 */
public class LBeanUtils extends BeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        return getPropertyNames(source, true);
    }

    public static String[] getNotNullPropertyNames(Object source) {
        return getPropertyNames(source, false);
    }

    private static String[] getPropertyNames(Object source) {
        return getPropertyNames(source, null);
    }

    private static String[] getPropertyNames(Object source, Boolean isNull) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (isNull == null || (isNull ? srcValue == null : srcValue != null)) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object source, Object target, boolean isExcludeNull) throws BeansException {
        if (isExcludeNull) {
            copyProperties(source, target, getNullPropertyNames(source));
        } else {
            copyProperties(source, target);
        }
    }

    /**
     * 复制指定属性
     *
     * @param source
     * @param target
     * @param properties
     * @throws BeansException
     */
    public static void copySpecifyProperties(Object source, Object target, String... properties) throws BeansException {
        if (properties != null) {
            String[] propertyNames = getPropertyNames(source);
            Map<String, String> propertyNamesMap = new HashMap<>();
            for (String propertyName : propertyNames) {
                propertyNamesMap.put(propertyName, propertyName);
            }
            for (String property : properties) {
                propertyNamesMap.remove(property);
            }
            String[] result = new String[propertyNamesMap.size()];
            propertyNamesMap.values().toArray(result);
            copyProperties(source, target, result);
        } else {
            copyProperties(source, target);
        }
    }

    public static void copyProperties(Object source, boolean isExcludeSourceNull, Object target, boolean isExcludeTargetNull) {
        Set<String> hs = new HashSet<>();
        if (isExcludeSourceNull) {
            hs.addAll(Arrays.asList(getNullPropertyNames(source)));
        }
        if (isExcludeTargetNull) {
            hs.addAll(Arrays.asList(getNullPropertyNames(target)));
        }
        if (hs.size() > 0)
            copyProperties(source, target, hs.toArray(new String[hs.size()]));
        else
            copyProperties(source, target);
    }

    /**
     * 把源bean的属性值赋值到目标bean中值为空的属性
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesToNull(Object source, Object target) {
        Set<String> hs = new HashSet<>();
        hs.addAll(Arrays.asList(getNotNullPropertyNames(target)));
        if (hs.size() > 0)
            copyProperties(source, target, hs.toArray(new String[hs.size()]));
        else
            copyProperties(source, target);
    }
}
