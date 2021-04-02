package com.personal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.flink.statefun.sdk.kafka.KafkaEgressSerializer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class MessageSerializer implements KafkaEgressSerializer<Message> {

    private static final String TOPIC = "stateful-output-topic";

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Message message) {
        byte[] key = message.getId().getBytes();

        JsonObject jObj = new JsonObject();
        jObj.addProperty("id", message.getId());
        jObj.addProperty("content", message.getContent());

        byte[] value = jObj.toString().getBytes();

        return new ProducerRecord<>(TOPIC, key, value);
    }
}
