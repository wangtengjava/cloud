package com.atguigu.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
//@Data 注解的主要作用是提高代码的简洁，使用这个注解可以省去代码中大量的get()、 set()、 toString()等方法；
@AllArgsConstructor
//有参构造
@NoArgsConstructor
//无参构造
//@Deprecated

public class Payment implements Serializable {
   private Long id;
   private String serial;
}
