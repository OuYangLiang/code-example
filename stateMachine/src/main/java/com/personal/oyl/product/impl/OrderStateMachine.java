package com.personal.oyl.product.impl;

import com.personal.oyl.product.StateMachine;

public class OrderStateMachine implements StateMachine<OrderState, OrderAction> {
    @Override
    public OrderState next(OrderState state, OrderAction action, Object context) {
        if (OrderAction.assign.equals(action)) {
            return OrderState.working;
        }

        if (OrderAction.process.equals(action)) {
            return OrderState.finished;
        }

        throw new RuntimeException();
    }
}
