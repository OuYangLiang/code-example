package com.personal.oyl.product.impl;

import com.personal.oyl.product.AbstractStateHandler;

public class OrderProcessHandler extends AbstractStateHandler<Order, OrderAction> {
    @Override
    protected Object doHandle(Order order) {
        System.out.println("处理订单");
        return null;
    }

    @Override
    public OrderAction action() {
        return OrderAction.process;
    }
}
