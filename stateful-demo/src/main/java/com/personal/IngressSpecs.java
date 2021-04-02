package com.personal;

import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.io.IngressSpec;
import org.apache.flink.statefun.sdk.kafka.KafkaIngressBuilder;
import org.apache.flink.statefun.sdk.kafka.KafkaIngressStartupPosition;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class IngressSpecs {

    public static final IngressIdentifier<Message> ID =
            new IngressIdentifier<>(Message.class, "ns", "input-ingress");

    public static final IngressSpec<Message> kafkaIngress =
            KafkaIngressBuilder.forIdentifier(ID)
                    .withKafkaAddress("localhost:9092")
                    .withConsumerGroupId("statefulGroup")
                    .withTopic("stateful-input-topic")
                    .withDeserializer(MessageDeserializer.class)
                    .withStartupPosition(KafkaIngressStartupPosition.fromLatest())
                    .build();
}
