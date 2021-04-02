package com.personal;

import org.apache.flink.statefun.sdk.io.Router;

/**
 * @author OuYang Liang
 * @since 2021-04-01
 */
public class MessageRouter implements Router<Message> {
    @Override
    public void route(Message message, Downstream<Message> downstream) {
        downstream.forward(TestFunction.type, message.getId(), message);
    }
}
