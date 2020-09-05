package com.atguigu.springcloud.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("************come in MyLogGateWatFilter: "+new Date());
        //获得请求中uname参数，判断请求中有没有uname参数,有就放行,没有返回错误状态码
        String uname=exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname==null){
            log.info("********用户名为null,非法用户,┭┮﹏┭┮");
            //给前台一个回应,返回一个HTPP状态码
            //HttpStatus.NOT_ACCEPTABLE 不被接收
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            //告诉前台你错在哪里
            return exchange.getResponse().setComplete();
        }
        //把当前判断通过的exchange再传下去,让下一个过滤器判断
        return chain.filter(exchange);
    }

    //加载当前过滤器的顺序,数字越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
