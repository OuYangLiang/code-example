package com.personal.oyl.stateMachine.handler;

import com.personal.oyl.stateMachine.AbstractStateHandler;
import com.personal.oyl.stateMachine.Event;
import com.personal.oyl.stateMachine.Order;

public class SignForHandler extends AbstractStateHandler {

    @Override
    protected void doHandler(Order order) {
        System.out.println("签收");
    }

    @Override
    protected void after(Order order) {
        System.out.println("定单处理完结");
    }

    @Override
    public Event event() {
        return Event.sign_for;
    }
}
