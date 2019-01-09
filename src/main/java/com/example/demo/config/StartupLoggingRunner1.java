package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/7 11:53
 **/
@Slf4j
@Component
@Order(value = 1)
public class StartupLoggingRunner1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("startup 1 ......");
    }
}
