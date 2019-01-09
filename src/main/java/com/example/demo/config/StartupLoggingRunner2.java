package com.example.demo.config;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/7 11:53
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 2)
public class StartupLoggingRunner2  implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("startup 2 ......");
    }
}
