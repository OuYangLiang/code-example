/*
 * File Name:Test.java
 * Author:ouyangliang2
 * Date:2016年10月24日
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
 
package com.personal.oyl.circuitBreaker;

import java.util.concurrent.TimeUnit;

import com.personal.oyl.circuitBreaker.concrete.ExceptionCircuitBreaker;
import com.personal.oyl.circuitBreaker.concrete.ExceptionCircuitBreakerInterpreptor;
import com.personal.oyl.circuitBreaker.concrete.TimeoutCircuitBreaker;
import com.personal.oyl.service.MyService;
import com.personal.oyl.service.ServiceFactory;

public class Test3
{
    public static void doStuff(MyService service, int flag) {
        try {
            String str = service.queryString(flag);
            
            System.out.println(str);
        }catch (CircuitBreakerException e) {
            System.out.println("service unavailable!!! + [" + e.getMessage() + "]");
        } catch(Throwable e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        CircuitBreaker cbe = new ExceptionCircuitBreaker(1, new ExceptionCircuitBreakerInterpreptor() {

            @Override
            public boolean shouldTrip(Throwable cause) {
                return cause instanceof RuntimeException;
            }
            
        });
        
        CircuitBreaker cb = new TimeoutCircuitBreaker(2000, 1, cbe);
        
        
        
        final MyService service = ServiceFactory.getService(cb);
        
        for (int i=0; i<=1; i++) {
            new Thread(new Runnable(){public void run(){
                while(true) {
                    doStuff(service, 0);
                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }}).start();
        }
        
        
        TimeUnit.MILLISECONDS.sleep(1000);
        
        doStuff(service, 1);
        
        TimeUnit.MILLISECONDS.sleep(2000);
        
        doStuff(service, 2);
        
        TimeUnit.MILLISECONDS.sleep(2000);
        
        
    }
    
}
