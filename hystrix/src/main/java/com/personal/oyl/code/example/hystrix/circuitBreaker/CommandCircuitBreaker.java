package com.personal.oyl.code.example.hystrix.circuitBreaker;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class CommandCircuitBreaker extends HystrixCommand<String> {

    private final String name;
    
    private static Setter setter() {
        HystrixCommandProperties.Setter props = HystrixCommandProperties.Setter();
        props.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD) // 信号量方式不支持超时。
            .withFallbackEnabled(true)
            .withFallbackIsolationSemaphoreMaxConcurrentRequests(100) // Fallback并发信号量，超过后快速失败
            .withExecutionTimeoutEnabled(false)
            .withExecutionTimeoutInMilliseconds(500)
            
        
            .withCircuitBreakerEnabled(true)
            .withCircuitBreakerErrorThresholdPercentage(20)
            .withCircuitBreakerForceClosed(false)
            .withCircuitBreakerForceOpen(false)
            .withCircuitBreakerRequestVolumeThreshold(10)
            .withCircuitBreakerSleepWindowInMilliseconds(5000);
        
        HystrixThreadPoolProperties.Setter prop2 = HystrixThreadPoolProperties.Setter();
        prop2.withCoreSize(5)
            .withMaximumSize(5)
            .withKeepAliveTimeMinutes(10000000)
            .withAllowMaximumSizeToDivergeFromCoreSize(false)
            .withMaxQueueSize(10);
        
        HystrixCommand.Setter rlt = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(props)
                .andThreadPoolPropertiesDefaults(prop2);
        
        return rlt;
    }

    public CommandCircuitBreaker(String name) {
        super(setter());
        this.name = name;
    }

    @Override
    protected String run() {
        try {
            TimeUnit.SECONDS.sleep(13);
        } catch (InterruptedException e) {
        }
        return "Hello " + name + "!";
    }
    
    @Override
    protected String getFallback() {
        return "something bad happened..." + name;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        for (int i = 1; i <= 100; i++) {
            new Thread(new TheRunner(i)).start();
        }
    }
    
    static class TheRunner implements Runnable {
        int i;
        TheRunner(int i) {
            this.i = i;
        }
        
        @Override
        public void run() {
            String s = new CommandCircuitBreaker(Integer.toString(i)).execute();
            System.out.println(s);
        }
    }
}