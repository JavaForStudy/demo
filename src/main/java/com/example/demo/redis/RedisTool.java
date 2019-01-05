package com.example.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/5 10:01
 **/
public class RedisTool {

    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String LOCK_SUCCESS = "OK";
    private static final long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     * @param jedis
     * @param lockKey   锁
     * @param requestId 锁的值
     * @param expireTime   过期时间
     * @return
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime){

        /*
         *   第二个为value，我们传的是requestId，其实就是key的value
         *   requestId即value主要用于解锁，
         *   通过给value赋值为requestId，在解锁的时候就可以有依据。
         *          谁上的锁就由谁来解决
         *   requestId可以使用UUID.randomUUID().toString()方法生成。
         *
         *   在相对旧一点的版本，requestId用一些额外的值来代替 比如是 当前时间戳+相对大一点的过期时间
         *   这是错误的：
         *      1 要求客户端的时间要同步
         *      2 如果多个客户端同时执行jedis.getSet()方法，那么虽然最终只有一个客户端可以加锁，
         *          但是这个客户端的锁的过期时间可能被其他客户端覆盖。
         *      3 任何客户端都可以解锁。
         *
         */
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)){
            return true;
        }

        return false;
    }


    /**
     * 解锁
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId){

        String script = "if redis.call('get', KEYS[1]) == ARGY[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS == Long.parseLong(result.toString())){
            return true;
        }
        return false;
    }




}
