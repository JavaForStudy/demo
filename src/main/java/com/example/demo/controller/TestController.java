package com.example.demo.controller;

import com.example.demo.common.DataBean;
import com.example.demo.service.ServiceManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName
 * @Description TODO
 * @Auth lan
 * @Date 2019/1/9 13:48
 **/
@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("/test1")
    public DataBean test(){
        ;
        return new DataBean(ServiceManager.testService.testFun());
    }


}
