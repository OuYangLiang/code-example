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
import com.personal.oyl.service.MyService;
import com.personal.oyl.service.ServiceFactory;


public class Test
{
    public static void doStuff(MyService service, int flag) {
        try {
            String str = service.queryString(flag);
            
            System.out.println(str);
        }catch (CircuitBreakerException e) {
            System.out.println("service unavailable!!!");
        } catch(Throwable e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
        CircuitBreaker cb = new ExceptionCircuitBreaker(1, new ExceptionCircuitBreakerInterpreptor() {

            @Override
            public boolean shouldTrip(Throwable cause) {
                return cause instanceof RuntimeException;
            }
            
        });
        
        final MyService service = ServiceFactory.getService(cb);
        
        doStuff(service, 0);
        doStuff(service, 0);
        doStuff(service, 0);
        doStuff(service, 2);
        doStuff(service, 0);
        doStuff(service, 0);
        doStuff(service, 0);
        
        try
        {
            TimeUnit.MILLISECONDS.sleep(6000);
        }
        catch(InterruptedException e1)
        {
        }
        
        new Thread(new Runnable(){public void run(){doStuff(service, 0);}}).start();
        new Thread(new Runnable(){public void run(){doStuff(service, 0);}}).start();
        new Thread(new Runnable(){public void run(){doStuff(service, 0);}}).start();
        
        try
        {
            TimeUnit.MILLISECONDS.sleep(2000);
        }
        catch(InterruptedException e1)
        {
        }
        
        doStuff(service, 0);
        doStuff(service, 0);
        doStuff(service, 0);
        
    }
    
}
