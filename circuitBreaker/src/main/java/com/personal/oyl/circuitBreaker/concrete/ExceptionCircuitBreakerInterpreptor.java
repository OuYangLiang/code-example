package com.personal.oyl.circuitBreaker.concrete;

public interface ExceptionCircuitBreakerInterpreptor {
    boolean shouldTrip(Throwable cause);
}
