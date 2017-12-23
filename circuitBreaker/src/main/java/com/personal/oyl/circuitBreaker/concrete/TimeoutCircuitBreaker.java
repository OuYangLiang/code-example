package com.personal.oyl.circuitBreaker.concrete;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.personal.oyl.circuitBreaker.BreakerState;
import com.personal.oyl.circuitBreaker.CircuitBreaker;
import com.personal.oyl.circuitBreaker.CircuitBreakerException;

public class TimeoutCircuitBreaker implements CircuitBreaker
{
    private long timeout = 2000l;
    private volatile BreakerState state = BreakerState.CLOSED;
    private AtomicLong lastFailure = new AtomicLong(0L);
    private AtomicLong resetMillis;
    
    private boolean isHardTrip = false;
    
    private AtomicBoolean isTestAllowed = new AtomicBoolean(true);
    
    private CircuitBreaker decorator;
    
    public TimeoutCircuitBreaker(long timeout, int secondsToReset) {
        this(timeout, secondsToReset, null);
    }
    
    public TimeoutCircuitBreaker(long timeout, long secondsToReset, CircuitBreaker decorator) {
        this.timeout = timeout;
        this.resetMillis = new AtomicLong(secondsToReset * 1000L);
        this.decorator = decorator;
    }

    @Override
    public <V> V invoke(Callable<V> c) throws Throwable
    {
        if (!allowRequest()) {
            throw new CircuitBreakerException("Timeout");
        }
        
        long start = System.currentTimeMillis();
        V result = null == this.decorator ? c.call() : this.decorator.invoke(c);
        if (System.currentTimeMillis() - start > timeout) {
            trip();
        } else {
            close();
        }
        
        return result;
    }

    @Override
    public void reset()
    {
        close();
        isHardTrip = false;
    }

    @Override
    public void tripHard()
    {
        this.trip();
        isHardTrip = true;
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
