package com.zz.test.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableDiscoveryClient  //激活eureka中的DiscoveryClient实现
@SpringBootApplication
public class WechatTestApp {

    public static void main(String[] args) {
    	SpringApplication.run(WechatTestApp.class, args);
    }
}