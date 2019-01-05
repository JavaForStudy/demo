package com.example.demo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils {

    /**
     * map转对象
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * redis map转对象,redis里时间格式yyyy-MM-dd HH:MM:ss或yyyy-MM-dd格式
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object redisMapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            if (field.getType() == Date.class) {
                if (map.get(field.getName()) != null) {
                    try {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
                        field.set(obj, dateFormater.parse(map.get(field.getName()).toString()));
                    } catch (Exception e) {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
                        field.set(obj, dateFormater.parse(map.get(field.getName()).toString()));
                    }
                } else
                    field.set(obj, null);
            } else
                field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /**
     * 对象转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (!"serialVersionUID".equals(field.getName())) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }

    /**
     * 对象转redis Map , 时间会格式化yyyy-MM-dd HH:MM:ss或yyyy-MM-dd格式,而不是现实时间搓
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, String> objectToRedisMap(Object obj) throws Exception {

        Map<String, Object> map = objectToMap(obj);
        if (map == null)
            return null;

        Map<String, String> data = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue() instanceof Date) {
                    try {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
                        data.put(entry.getKey(), dateFormater.format(entry.getValue()));
                    } catch (Exception e) {
                        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
                        data.put(entry.getKey(), dateFormater.format(entry.getValue()));
                    }
                } else
                    data.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return data;
    }
}