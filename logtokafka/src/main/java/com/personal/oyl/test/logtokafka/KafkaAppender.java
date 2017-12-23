package com.personal.oyl.test.logtokafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    
    private static final Formatter formatter = new Formatter();
    
    private KafkaProducer<String, String> producer;
    private String topic;
    private String bootstrapServer;
    private String clientId;

    @Override
    protected void append(ILoggingEvent event) {
        producer.send(new ProducerRecord<>(topic, formatter.format(event)));
    }

    @Override
    public void start() {
        super.start();
        producer = new KafkaProducer<>(init());
    }

    @Override
    public void stop() {
        super.stop();
        producer.close();
    }
    
    private Properties init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        return props;
    }
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    static class Formatter {
        public String format(ILoggingEvent event) {
            Gson g = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("level", event.getLevel().levelStr);
            map.put("message", event.getFormattedMessage());
            map.put("timestamp", event.getTimeStamp());
            map.put("logger", event.getLoggerName());
            return g.toJson(map);
        }
    }

}
