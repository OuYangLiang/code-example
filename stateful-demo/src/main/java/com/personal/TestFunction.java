package com.personal;

import org.apache.flink.statefun.sdk.Context;
import org.apache.flink.statefun.sdk.FunctionType;
import org.apache.flink.statefun.sdk.StatefulFunction;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class TestFunction implements StatefulFunction {

    public static final FunctionType type = new FunctionType("ns", "test");

//    @Persisted
//    private final PersistedValue<Integer> count = PersistedValue.of("count", Integer.class);

    @Override
    public void invoke(Context context, Object input) {
        Message message = (Message) input;
        Message response = Message.newBuilder()
                .setId(message.getId())
                .setContent("Hello " + message.getContent())
                .build();
        context.send(EgressSpecs.ID, response);
    }
}
