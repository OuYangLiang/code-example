package com.personal.oyl.event.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.personal.oyl.event.EventConsumer;
import com.personal.oyl.event.EventSubmitter;
import com.personal.oyl.event.SubscriberConfig;
import com.personal.oyl.event.web.subscribers.Sub1;

@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private EventSubmitter submitter;
    
    @Autowired
    private SubscriberConfig config;
    
    @Autowired
    private EventConsumer consumer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            config.addSubscriber("Event Type", new Sub1());
            
            Thread submitterThread = new Thread(submitter);
            Thread consumerThread  = new Thread(consumer);
            
            /*submitterThread.start();
            consumerThread.start();
            
            Runtime.getRuntime().addShutdownHook( new Thread(() -> {
                
                System.out.println("start shuting down...");
                
                submitterThread.interrupt();
                consumer.wake();
                
                System.out.println("shut down...");
            }));*/
        }
    }

}
