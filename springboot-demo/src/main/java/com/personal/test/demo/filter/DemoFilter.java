package com.personal.test.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info(request.getRemoteAddr());
        chain.doFilter(request, response);
    }

}
