package com.personal.test.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DemoExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(DemoExceptionHandler.class);

    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    String handleControllerException(Exception e) {
        log.error(e.getMessage(), e);
        return "Error occured: " + e.getMessage();
    }
}
