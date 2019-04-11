package com.personal.oyl.myb.statemachine;

import java.util.HashMap;
import java.util.Map;

import com.personal.oyl.myb.statemachine.handler.EnterprisePayHandler;
import com.personal.oyl.myb.statemachine.handler.EnterpriseRechargeHandler;
import com.personal.oyl.myb.statemachine.handler.IndividualPayHandler;
import com.personal.oyl.myb.statemachine.handler.NoactionHandler;

public final class StateMachineEngine {
    public static void post(Member member, Event event) {
        StateHandler h = getHandler(member.getType(), member.getState(), event);
        
        if (null == h) {
            throw new IllegalStateException();
        }
        
        h.handle(member);
    }
    
    public static StateHandler getHandler(MemberType type, State state, Event event) {
        if (null == holder) {
            synchronized (StateMachineEngine.class) {
                if (null == holder) {
                    init();
                }
            }
        }
        
        String key = type.name() + ":" + state.name() + ":" + event.name();
        return holder.get(key);
    }
    
    private static Map<String, StateHandler> holder;
    
    private static void init() {
        holder = new HashMap<>();
        holder.put("enterprise:pending_active:recharge", new EnterpriseRechargeHandler());
        holder.put("enterprise:lost:recharge", new EnterpriseRechargeHandler());
        
        holder.put("enterprise:active:pay", new EnterprisePayHandler());
        holder.put("enterprise:lost:pay", new EnterprisePayHandler());
        
        holder.put("enterprise:pending_active:no_action", new NoactionHandler());
        holder.put("enterprise:online:no_action", new NoactionHandler());
        holder.put("enterprise:active:no_action", new NoactionHandler());
        
        
        holder.put("individual:active:no_action", new NoactionHandler());
        holder.put("individual:pending_active:pay", new IndividualPayHandler());
        holder.put("individual:lost:pay", new IndividualPayHandler());
    }
}
