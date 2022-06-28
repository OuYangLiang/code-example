package com.personal.oyl.product.impl;

import com.personal.oyl.product.AbstractStateHandler;

public class OrderAssignHandler extends AbstractStateHandler<Order, OrderAction> {
    @Override
    protected Object doHandle(Order order) {
        System.out.println("分配订单");
        return null;
    }

    @Override
    public OrderAction action() {
        return OrderAction.assign;
    }
}
