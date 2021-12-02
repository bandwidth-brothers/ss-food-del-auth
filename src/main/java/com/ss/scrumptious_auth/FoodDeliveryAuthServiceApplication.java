package com.ss.scrumptious_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
@EntityScan(basePackages = {"com.ss.scrumptious.common_entities.entity"})
public class FoodDeliveryAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryAuthServiceApplication.class, args);
    }

}
