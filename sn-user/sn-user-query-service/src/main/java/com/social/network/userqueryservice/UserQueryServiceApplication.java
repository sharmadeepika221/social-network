package com.social.network.userqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.social.network")
public class UserQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserQueryServiceApplication.class, args);
    }
}
