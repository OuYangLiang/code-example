package com.personal.oyl.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;

public class EventSubmitter implements Runnable {
    
    @Autowired
    private EventMapper mapper;

    @Override
    public void run() {
        
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        try {
            List<Long> eventIds = new LinkedList<>();
            List<Future<RecordMetadata>> futures = new LinkedList<>();
            
            while (true) {
                List<Event> list = mapper.queryTopN(100);
                if (null != list && !list.isEmpty()) {
                    for (Event event : list) {
                        ProducerRecord<Integer, String> record = new ProducerRecord<>("topic", 0, event.getEventTime().getTime(), 0, "value", null);
                        futures.add(producer.send(record));
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
                        mapper.batchClean(eventIds);
                    }
                }
            }
        } finally {
            producer.close();
        }
        
    }

}