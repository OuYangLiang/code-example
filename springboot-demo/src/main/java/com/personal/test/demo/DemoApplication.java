package com.personal.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * springboot 启动类
 */
@ImportResource("classpath*:/spring*.xml")
@SpringBootApplication
public class DemoApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
