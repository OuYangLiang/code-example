package com.personal.test.demo.eurekaconsumer3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaConsumer3Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumer3Application.class, args);
    }
}
