package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/9 13:52
 **/
@Component
public class ServiceManager {

    public static TestService testService;

    @Autowired
    public void setTestService(TestService testService) {
        ServiceManager.testService = testService;
    }
}
