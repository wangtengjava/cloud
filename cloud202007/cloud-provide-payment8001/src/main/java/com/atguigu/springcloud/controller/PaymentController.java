package com.atguigu.springcloud.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.atguigu.springcloud.entity.CommonResult;
import com.atguigu.springcloud.entity.Payment;
import com.atguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService service;
    @Resource
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    private String serverPor;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody  Payment payment){
        int result=service.create(payment);
        log.info("******插入结果:"+result);
        if (result>0){
            return  new CommonResult(200,"插入数据库成功,serverPort "+serverPor,result);
        }else{
            return  new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment =service.getPaymentById(id);
        log.info("******插入结果:"+payment);
        System.out.printf("123");
        if (payment!=null){
            return  new CommonResult(200,"查询成功 serverPort"+serverPor,payment);
        }else{
            return  new CommonResult(444,"没有对应记录,查询ID:"+id,null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        //获取eureka上面注册的所有服务器
        List<String> services = discoveryClient.getServices();
        for (String s : services) {
            log.info("*****element: "+s);
        }
        System.out.printf("1");
        //获取eureka上面固定服务器的基本信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("*****element: "+instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
    return this.discoveryClient;
    }

    @GetMapping(value= "/payment/lb")
    public String getPaymentLB(){
        return serverPor;
    }

    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPor;
    }
}
