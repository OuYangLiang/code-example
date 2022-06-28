package com.personal.oyl.product.impl;

import com.personal.oyl.product.Principal;
import com.personal.oyl.product.StateMachine;

public class Order implements Principal<OrderState, OrderAction> {
    private OrderState state;

    @Override
    public void setState(OrderState orderState) {
        this.state = orderState;
    }

    @Override
    public OrderState getState() {
        return this.state;
    }

    @Override
    public StateMachine<OrderState, OrderAction> machine() {
        return new OrderStateMachine();
    }
}
