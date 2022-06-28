package com.personal.oyl.product.impl;

import com.personal.oyl.product.StateHandler;

import java.util.HashMap;
import java.util.Map;

public final class OrderStateMachineEngine {
    public static void post(Order order, OrderAction action) {
        StateHandler<Order, OrderAction> h = getHandler(order, action);
        
        if (null == h) {
            throw new IllegalStateException();
        }
        
        h.handle(order);
    }
    
    public static StateHandler<Order, OrderAction> getHandler(Order order, OrderAction action) {
        if (null == holder) {
            synchronized (OrderStateMachineEngine.class) {
                if (null == holder) {
                    init();
                }
            }
        }
        
        String key = order.getState().name() + ":" + action.name();
        return holder.get(key);
    }
    
    private static volatile Map<String, StateHandler<Order, OrderAction>> holder;
    
    private static void init() {
        holder = new HashMap<>();
        holder.put("pending:assign", new OrderAssignHandler());
        holder.put("working:process", new OrderProcessHandler());
    }
}
