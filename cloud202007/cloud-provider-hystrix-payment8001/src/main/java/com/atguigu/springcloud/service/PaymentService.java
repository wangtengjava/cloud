package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_ok(Integer id) {
        return "线程池: " + Thread.currentThread().getName() + "  paymentInfo_ok,id  " + id + "\t" + "O(∩_∩)O哈哈";
    }

    /**
     * @HystrixCommand注解下报异常?
     * 一旦调用服务方法失败并抛出了错误信息后，会自动调用
     * @HystrixCommand标注好的fallbackMethod调用类中的指定方法
     *
     * commandProperties{@HystrixProperty...}干什么的?
     *就是控制当前的线程超时时间,设置了3秒就是3秒内就是正常，
     * 超过三秒就是错误会去走fallbackMethod指定的方法
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int num=1/0;
        int timeNnmber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNnmber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: " + Thread.currentThread().getName() + "  paymentInfo_TimeOut,id  " + id + "\t" + "O(∩_∩)O哈哈" + "  耗时(秒):" + timeNnmber;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池: " + Thread.currentThread().getName() + "  8001系统繁忙或者运行报错,请稍后再试,id  " + id + "\t" + "┭┮﹏┭┮";
    }

    //--------------下面是服务熔断
    /**
     * 下面这个注解的意思就是,这个方法在10次请求里面有百分之60出现错误,
     * 就会开启断路器，然后在10秒后进入半开尝试状态
     *
     * 你怎么知道要配置这些参数的?
     * 在HystrixCommandProperties类里面都有默认的,
     * 和springboot一样,你配置了就用你配置的,你没配置就用默认的。
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        /**
         * 上面那个代码等价于
         *         UUID.randomUUID().toString().replace("-","");
         */
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号: "+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数,请稍后再试 ~~ id:"+id;
    }
}