package com.social.network.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.social.network")
public class UserServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
