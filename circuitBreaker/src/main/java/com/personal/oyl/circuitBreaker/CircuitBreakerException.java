/*
 * File Name:CircuitBreakerException.java
 * Author:ouyangliang2
 * Date:2016年10月24日
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */

package com.personal.oyl.circuitBreaker;

public class CircuitBreakerException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public CircuitBreakerException()
    {
        super();
    }

    public CircuitBreakerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CircuitBreakerException(String message)
    {
        super(message);
    }

    public CircuitBreakerException(Throwable cause)
    {
        super(cause);
    }
}
