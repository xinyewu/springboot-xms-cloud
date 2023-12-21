package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient//服务注册发现
public class ResIndexApp {
    public static void main(String[] args) {
        SpringApplication.run(ResIndexApp.class,args);
    }
}
