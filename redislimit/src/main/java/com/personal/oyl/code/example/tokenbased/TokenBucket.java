package com.personal.oyl.code.example.tokenbased;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucket {
    private final static int DEFAULT_SIZE = 100;
    private final static int DEFAULT_BATCH = 1;
    private final static int DEFAULT_FREQ  = 1;
    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    
    private AtomicInteger a = new AtomicInteger(0);
    private final int size;
    
    public TokenBucket() {
        this(DEFAULT_SIZE, DEFAULT_BATCH, DEFAULT_FREQ, DEFAULT_TIME_UNIT);
    }
    
    public TokenBucket(int size, int batch, int freq, TimeUnit unit) {
        this.size = size;
        
        new Thread(new TokenProducer(batch, freq, unit)).start();
    }
    
    public boolean acquire() {
        while (true) {
            int current = a.get();
            if (current == 0) {
                return false;
            }
            
            if (a.compareAndSet(current, current - 1)) {
                return true;
            }
        }
    }
    
    public void release(int n) {
        while (true) {
            int current = a.get();
            int next = current + n;
            if (next > size) {
                next = size;
            }
            
            if (a.compareAndSet(current, next)) {
                return;
            }
        }
    }
    
    public int current() {
        return a.get();
    }
    
    class TokenProducer implements Runnable {
        
        private int batch;
        private int freq;
        private TimeUnit unit;
        
        public TokenProducer(int batch, int freq, TimeUnit unit) {
            this.batch = batch;
            this.freq = freq;
            this.unit = unit;
        }
        
        @Override
        public void run() {
            while (true) {
                try {
                    TokenBucket.this.release(this.batch);
                    
                    try {
                        unit.sleep(freq);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // ignore
                }
            }
        }

    }
}
