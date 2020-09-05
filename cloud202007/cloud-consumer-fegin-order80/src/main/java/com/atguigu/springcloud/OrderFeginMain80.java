package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//激活并使用Fegin
@EnableFeignClients
public class OrderFeginMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeginMain80.class,args);
    }
}
