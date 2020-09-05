package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//使用Feign
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeginService {

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout();
}
