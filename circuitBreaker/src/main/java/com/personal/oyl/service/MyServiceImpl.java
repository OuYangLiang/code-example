package com.personal.oyl.service;

import java.util.concurrent.TimeUnit;

public class MyServiceImpl implements MyService {
    public String queryString(int flag) {
        
        if (flag == 0) {
            return "Hello World!!!";
        } else if (flag == 1){
            try
            {
                TimeUnit.MILLISECONDS.sleep(2100);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            
            return "hello World!!!";
        }
        else {
            throw new RuntimeException("it's a fuck!!!");
        }
        
    }
}
