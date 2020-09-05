package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

//    RouteLocator 路由  RouteLocatorBuilder 路由构建器
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        //相当于yml里面得routes
        RouteLocatorBuilder.Builder routes = builder.routes();
        //就是你访问9527端口得"/guonei",就会转发到"//http://news.baidu.com/guonei"
        routes.route("path_route_atguigu",
                r ->r.path("/guonei").
                        uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
}
