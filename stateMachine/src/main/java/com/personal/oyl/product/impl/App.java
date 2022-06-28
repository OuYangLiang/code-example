package com.personal.oyl.product.impl;

public class App {
    public static void main(String[] args) {
        Order order = new Order();
        order.setState(OrderState.pending);

        OrderStateMachineEngine.post(order, OrderAction.assign);
        OrderStateMachineEngine.post(order, OrderAction.process);
    }

}
