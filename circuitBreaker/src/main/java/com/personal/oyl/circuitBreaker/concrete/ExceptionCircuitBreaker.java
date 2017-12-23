package com.personal.oyl.circuitBreaker.concrete;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.personal.oyl.circuitBreaker.BreakerState;
import com.personal.oyl.circuitBreaker.CircuitBreaker;
import com.personal.oyl.circuitBreaker.CircuitBreakerException;

public class ExceptionCircuitBreaker implements CircuitBreaker
{
    private volatile BreakerState state = BreakerState.CLOSED;
    private AtomicLong lastFailure = new AtomicLong(0L);
    private AtomicLong resetMillis;
    
    private boolean isHardTrip = false;
    
    private AtomicBoolean isTestAllowed = new AtomicBoolean(true);
    private ExceptionCircuitBreakerInterpreptor interceptor;
    private CircuitBreaker decorator;
    
    public ExceptionCircuitBreaker(int secondsToReset, ExceptionCircuitBreakerInterpreptor interceptor) {
        this(secondsToReset, interceptor, null);
    }
    
    public ExceptionCircuitBreaker(int secondsToReset, ExceptionCircuitBreakerInterpreptor interceptor, CircuitBreaker decorator) {
        this.resetMillis = new AtomicLong(secondsToReset * 1000L);
        this.interceptor = interceptor;
        this.decorator = decorator;
    }

    @Override
    public <V> V invoke(Callable<V> c) throws Throwable {
        if (!allowRequest()) {
            throw new CircuitBreakerException("Error");
        }
        
        try {
            V result = null == this.decorator ? c.call() : this.decorator.invoke(c);
            close();
            return result;
        } catch (CircuitBreakerException e) {
            throw e;
        } catch (Throwable cause) {
            if (interceptor.shouldTrip(cause.getCause())) {
                trip();
            }
            
            throw cause;
        }

    }
    
    @Override
    public void tripHard() {
        this.trip();
        isHardTrip = true;
    }
    
    @Override
    public void reset() {
        close();
        isHardTrip = false;
    }
    
    private boolean allowRequest() {
        if (this.isHardTrip) {
            return false;
        }
        
        if (BreakerState.CLOSED == state) {
            return true;
        }

        if (BreakerState.OPEN == state && System.currentTimeMillis() - lastFailure.get() >= resetMillis.get()) {
            state = BreakerState.HALF_CLOSED;
        }
        
        if (BreakerState.HALF_CLOSED == state && isTestAllowed.compareAndSet(true, false)) {
            return true;
        }
        
        return false;
    }
    
    private void close() {
        state = BreakerState.CLOSED;
        isTestAllowed.set(true);
    }
    
    private void trip() {
        state = BreakerState.OPEN;
        isTestAllowed.set(true);
        lastFailure.set(System.currentTimeMillis());
    }
    
}
