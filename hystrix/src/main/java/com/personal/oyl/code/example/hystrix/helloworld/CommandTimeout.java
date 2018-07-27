package com.personal.oyl.code.example.hystrix.helloworld;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class CommandTimeout extends HystrixCommand<String> {

    private final String name;
    
    private static Setter setter() {
        HystrixCommandProperties.Setter props = HystrixCommandProperties.Setter();
        props.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD) // 信号量方式不支持超时。
            .withFallbackEnabled(true)
            .withFallbackIsolationSemaphoreMaxConcurrentRequests(100) // Fallback并发信号量，超过后快速失败
            .withExecutionTimeoutEnabled(true)
            .withExecutionTimeoutInMilliseconds(500);
        
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(props);
    }

    public CommandTimeout(String name) {
        super(setter());
        this.name = name;
    }

    @Override
    protected String run() {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
        }
        return "Hello " + name + "!";
    }
    
    @Override
    protected String getFallback() {
        return "something bad happened...";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String s = new CommandTimeout("Bob").execute();
        System.out.println(s);
    }
}