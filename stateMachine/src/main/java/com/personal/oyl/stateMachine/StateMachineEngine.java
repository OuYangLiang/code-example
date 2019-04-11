package com.personal.oyl.stateMachine;

import java.util.HashMap;
import java.util.Map;

import com.personal.oyl.stateMachine.handler.DeliverHandler;
import com.personal.oyl.stateMachine.handler.PayHandler;
import com.personal.oyl.stateMachine.handler.SignForHandler;

public final class StateMachineEngine {
    public static void post(Order order, Event event) {
        getHandler(order.getState(), event).handle(order);
    }
    
    public static StateHandler getHandler(State state, Event event) {
        if (null == holder) {
            synchronized (StateMachineEngine.class) {
                if (null == holder) {
                    init();
                }
            }
        }
        
        String key = state.name() + "-" + event.name();
        return holder.get(key);
    }
    
    private static Map<String, StateHandler> holder;
    
    private static void init() {
        holder = new HashMap<>();
        holder.put("pending_payment-pay", new PayHandler());
        holder.put("pending_delivery-deliver", new DeliverHandler());
        holder.put("pending_sign_for-sign_for", new SignForHandler());
    }
}
