package com.personal.oyl.event.web;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.personal.oyl.event.EventConsumer;
import com.personal.oyl.event.EventSubmitter;
import com.personal.oyl.event.SubscriberConfig;
import com.personal.oyl.event.Worker;
import com.personal.oyl.event.web.subscribers.Sub1;

@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {
    
    
    @Autowired
    private SubscriberConfig config;
    
    @Autowired
    private Worker worker;
    

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            config.addSubscriber("Event Type", new Sub1());
            
            /*Thread submitterThread = new Thread(submitter);
            Thread consumerThread  = new Thread(consumer);
            
            submitterThread.start();
            consumerThread.start();
            
            Runtime.getRuntime().addShutdownHook( new Thread(() -> {
                
                System.out.println("start shuting down...");
                
                submitterThread.interrupt();
                consumer.wake();
                
                System.out.println("shut down...");
            }));*/
            
            try {
                worker.start();
            } catch (IOException | InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }
    }

}
