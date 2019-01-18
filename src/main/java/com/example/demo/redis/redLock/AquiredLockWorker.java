package com.example.demo.redis.redLock;

/**
 * @ClassName
 * @Description 获取锁后需要处理的逻辑
 * @Auth lan
 * @Date 2019/1/15 12:58
 **/
public interface AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;

}
