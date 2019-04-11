package com.personal.oyl.stateMachine.handler;

import com.personal.oyl.stateMachine.AbstractStateHandler;
import com.personal.oyl.stateMachine.Event;
import com.personal.oyl.stateMachine.Order;

public class DeliverHandler extends AbstractStateHandler {

    @Override
    protected void doHandler(Order order) {
        System.out.println("发货");
    }

    @Override
    public Event event() {
        return Event.deliver;
    }

}
