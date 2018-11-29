package com.personal.test.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public final class DemoExceptionHandler {

    private static final Logger LOG =
            LoggerFactory.getLogger(DemoExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    String handleControllerException(final Exception e) {
        LOG.error(e.getMessage(), e);
        return "Error occured: " + e.getMessage();
    }
}
