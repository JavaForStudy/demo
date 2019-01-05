package com.example.demo.interceptor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * 简化 redis 分布式锁的使用
 * 使用方法如下
 *
 * <pre>
 * {@code
 *
 *   @RedisLock(key = "#user.id")
 *   @Transactional(rollbackFor = Exception.class)
 *   public void update(User user){
 *       logger.info("updating user {}", user);
 *   }
 *
 * }
 * </code>
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisLock {


    /**
     * redis 锁前缀, 如果没有, 默认 类名 + 方法名
     * 最后都会统一加上系统名, 最终结果如下
     * example： tms:UserService:save:xx
     * @return
     */
    String lockPrefix() default "";

    /**
     * 必填
     * 业务自定义 key, 会通过 spel 表达式解析
     * 使用方法同 spring cache 那个 key
     * @see org.springframework.cache.annotation.Cacheable#key()
     * @return
     */
    String key();


    /**
     * 锁默认超时时间
     * @return
     */
    long timeout() default 300;


    /**
     * 锁默认超时时间, 单位为秒
     * 因这命令只支持秒 EXPIRE key seconds
     * 所以不能填小于秒的字段
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}