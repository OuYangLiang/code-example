package com.personal.oyl.test.logtokafka;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger log = LoggerFactory.getLogger(App.class);
    
    public static void main( String[] args )
    {
        for (int i = 1; i <= 10; i++) {
            log.info( "Message ... " + i );
        }
        
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
