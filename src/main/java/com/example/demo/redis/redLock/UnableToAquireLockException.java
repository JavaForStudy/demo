package com.example.demo.redis.redLock;

/**
 * @ClassName
 * @Description 异常类
 * @Auth lan
 * @Date 2019/1/15 13:01
 **/
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
