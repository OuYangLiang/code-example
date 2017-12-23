package com.personal.oyl.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import com.personal.oyl.circuitBreaker.CircuitBreaker;
import com.personal.oyl.circuitBreaker.CircuitBreakerException;

public class ServiceFactory {

    public static MyService getService(final CircuitBreaker cb) {

        final MyService service = new MyServiceImpl();
        
        return (MyService) Proxy.newProxyInstance(service.getClass()
                .getClassLoader(), service.getClass().getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, final Method method,
                            final Object[] args) throws Throwable {
                        
                        try {
                            Object rlt = cb.invoke(new Callable<Object>() {

                                @Override
                                public Object call() throws Exception {
                                    return method.invoke(service, args);
                                }
                                
                            });
                            
                            return rlt;
                        } catch (CircuitBreakerException e ) {
                            throw e;
                        } catch (InvocationTargetException e) {
                            throw e.getCause();
                        }
                    }

                });

    }
}
