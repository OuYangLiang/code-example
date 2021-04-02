package com.personal;

import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.io.EgressSpec;
import org.apache.flink.statefun.sdk.kafka.KafkaEgressBuilder;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class EgressSpecs {
    public static final EgressIdentifier<Message> ID =
            new EgressIdentifier<>("ns", "output-egress", Message.class);

    public static final EgressSpec<Message> kafkaEgress =
            KafkaEgressBuilder.forIdentifier(ID)
                    .withKafkaAddress("localhost:9092")
                    .withSerializer(MessageSerializer.class)
                    .build();
}
