package com.zinko.servicebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class ServiceBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBookApplication.class, args);
    }

}
