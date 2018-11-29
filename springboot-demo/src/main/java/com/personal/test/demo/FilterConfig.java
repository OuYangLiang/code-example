package com.personal.test.demo;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.personal.test.demo.filter.DemoFilter;


@Configuration
public class FilterConfig {

    /**
     * 配置Demofilter
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registration =
                new FilterRegistrationBean<>();
        registration.setOrder(-1);
        registration.setFilter(new DemoFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
