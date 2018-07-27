package com.personal.oyl.code.example.hystrix.fallback;

import java.util.concurrent.ExecutionException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandFallback extends HystrixCommand<String> {


    public CommandFallback() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() {
        throw new RuntimeException("error occured...");
    }
    
    
    
    @Override
    protected String getFallback() {
        return "something bad happened...";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String s = new CommandFallback().execute();
        System.out.println(s);
    }
}