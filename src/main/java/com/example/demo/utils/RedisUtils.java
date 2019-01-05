package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by admin on 16/4/6.
 */
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /************************************************* hset/hget start*****************************************************/

    /**
     * 以hash set方式保存对象,对象里面不能包含对象
     *
     * @param key
     * @param obj
     * @throws Exception
     */
    public void hset(String key, Object obj) throws Exception {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map map = EntityUtils.objectToRedisMap(obj);
        if (map != null)
            hashOperations.putAll(key, map);
    }

    /**
     * 以hash set方式保存对象,对象里面不能包含对象
     *
     * @param key
     * @param obj
     * @param timeout
     * @param timeUnit
     * @throws Exception
     */
    public void hset(String key, Object obj, long timeout, TimeUnit timeUnit) throws Exception {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map map = EntityUtils.objectToRedisMap(obj);
        if (map != null)
            hashOperations.putAll(key, map);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 取得hash get对象
     *
     * @param key
     * @param clazz
     * @return
     * @throws Exception
     */
    public Object hget(String key, Class clazz) throws Exception {
        if (redisTemplate.hasKey(key)) {
            HashOperations hashOperations = redisTemplate.opsForHash();
            Map<String, Object> data = hashOperations.entries(key);
            if (data != null) {
                return EntityUtils.redisMapToObject(data, clazz);
            }
        }
        return null;
    }

    /************************************************* hset/hget end*****************************************************/

    /**
     * 根据key,删除
     *
     * @param key
     * @throws Exception
     */
    public void del(String key) throws Exception {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     * @throws Exception
     */
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) throws Exception {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 是否有存在key
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Boolean hasKey(String key) throws Exception {
        return redisTemplate.hasKey(key);
    }

    /************************************************* set/get start*****************************************************/

    /**
     * set保存
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public void set(String key, String value) throws Exception {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set保存
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public void setObjectToJson(String key, Object value) throws Exception {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    /**
     * set保存
     *
     * @param key
     * @param value
     * @param timeout
     * @throws Exception
     */
    public void set(String key, String value, long timeout) throws Exception {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * set保存
     *
     * @param key
     * @param value
     * @param timeout
     * @throws Exception
     */
    public void setObjectToJson(String key, Object value, long timeout) throws Exception {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeout);
    }

    /**
     * set保存
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @throws Exception
     */
    public void set(String key, String value, long timeout, TimeUnit timeUnit) throws Exception {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * set保存
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @throws Exception
     */
    public void setObjectToJson(String key, Object value, long timeout, TimeUnit timeUnit) throws Exception {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeout, timeUnit);
    }

    /**
     * get取
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * get取
     *
     * @param key
     * @param clazz
     * @return
     * @throws Exception
     */
    public Object getObject(String key, Class clazz) throws Exception {
        if (redisTemplate.hasKey(key)) {
            String value = redisTemplate.opsForValue().get(key);
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    /************************************************* set/get end *****************************************************/

    /************************************************* List start*******************************************************/

    /**
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 范围检索
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /************************************************* List end ********************************************************/

    public <T> T doInRedisLock(String key, long timeout, Function<Boolean, T> function) {

        BoundValueOperations valueOps = redisTemplate.boundValueOps(key);

        if (valueOps.setIfAbsent(System.currentTimeMillis() + "")) { // 确保只有一个请求进入这里
            try {
                valueOps.expire(timeout, TimeUnit.SECONDS); // 设置超时
                return function.apply(true);
                // Thread.sleep(20000); // 假设程序执行要 20 秒
            } finally {
                redisTemplate.delete(key);
            }
        } else {

            // -1 表示不会过期, 可能上次并发时获取不到连接导致的
            if (valueOps.getExpire().intValue() == -1) {

                String thenStr = valueOps.get() + "";
                if(thenStr.trim().length() > 0 && thenStr.matches("\\d+")) {
                    // 计算新的超时时间, 如果已经超时
                    long then = Long.valueOf(thenStr);

                    long now = System.currentTimeMillis();
                    long elapsed = (now - then) / 1000;

                    if (elapsed > timeout) {
                        redisTemplate.delete(key);
                    } else {
                        valueOps.expire(timeout - elapsed, TimeUnit.SECONDS);
                    }
                } else {
                    redisTemplate.delete(key);
                }
            }

            return function.apply(false);
        }
    }


}


