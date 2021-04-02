package com.personal;

import com.google.gson.*;
import org.apache.flink.statefun.sdk.kafka.KafkaIngressDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class MessageDeserializer implements KafkaIngressDeserializer<Message> {

    @Override
    public Message deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) {
        JsonParser parser = new JsonParser();
        JsonElement jEle = parser.parse(new String(consumerRecord.value()));

        JsonObject jObj = (JsonObject) jEle;
        String id = jObj.get("id").getAsString();
        String content = jObj.get("content").getAsString();

        return Message.newBuilder().setId(id).setContent(content).build();
    }
}
