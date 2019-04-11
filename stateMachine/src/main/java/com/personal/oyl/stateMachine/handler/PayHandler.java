package com.personal.oyl.stateMachine.handler;

import com.personal.oyl.stateMachine.AbstractStateHandler;
import com.personal.oyl.stateMachine.Event;
import com.personal.oyl.stateMachine.Order;

public class PayHandler extends AbstractStateHandler {

    @Override
    protected void before(Order order) {
        System.out.println("待付款定单，准备付款");
    }

    @Override
    protected void after(Order order) {
        System.out.println("付款成功");
    }

    @Override
    protected void doHandler(Order order) {
        System.out.println("定单付款");
    }

    @Override
    public Event event() {
        return Event.pay;
    }

}
