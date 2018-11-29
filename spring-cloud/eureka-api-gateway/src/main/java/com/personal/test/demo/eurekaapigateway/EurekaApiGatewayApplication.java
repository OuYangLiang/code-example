package com.personal.test.demo.eurekaapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.personal.test.demo.eurekaapigateway.filter.Filter;

@EnableZuulProxy
@SpringBootApplication
public class EurekaApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApiGatewayApplication.class, args);
    }
    
    @Bean
    public Filter filter() {
        return new Filter();
    }
}
