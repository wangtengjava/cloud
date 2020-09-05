package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService service;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        String result=service.paymentInfo_ok(id);
        log.info("*****result: "+result);
        return result;
    }

    @GetMapping("/payment/hystrix/TimeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result=service.paymentInfo_TimeOut(id);
        log.info("*****result: "+result);
        return result;
    }

    //------下面是服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result=service.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }
}
