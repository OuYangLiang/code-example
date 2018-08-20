package com.personal.oyl.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSubmitter implements Runnable {
    
    @Autowired
    private EventMapper mapper;
    
    @Override
    public void run() {
        
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        
        try {
            List<Long> eventIds = new LinkedList<>();
            List<Future<RecordMetadata>> futures = new LinkedList<>();
            
            while (true) {
                Map<String, Object> param = new HashMap<>();
                param.put("limit", Integer.valueOf(100));
                param.put("tbNum", 0);
                
                List<Event> list = mapper.queryTopN(param);
                
                if (null == list || list.isEmpty()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }
                
                for (Event event : list) {
                    ProducerRecord<String, String> record = new ProducerRecord<>("event-topic", 0,
                            event.getEventTime().getTime(), null, event.json(), null);
                    futures.add(producer.send(record));
                    eventIds.add(event.getId());
                }
                
                boolean failed = false;
                for(Future<RecordMetadata> future : futures) {
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        failed = true;
                        break;
                    }
                }
                
                if (!failed) {
                    Map<String, Object> param2 = new HashMap<>();
                    param2.put("list", eventIds);
                    param2.put("tbNum", 0);
                    mapper.batchClean(param2);
                }
                
            }
        } finally {
            producer.close();
        }
        
    }
    
}