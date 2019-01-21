package com.personal.test.demo.eurekaconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class EurekaConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConfigApplication.class, args);
    }
}
