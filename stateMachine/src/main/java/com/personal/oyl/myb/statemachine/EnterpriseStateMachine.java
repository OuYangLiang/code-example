package com.personal.oyl.myb.statemachine;

public class EnterpriseStateMachine implements StateMachine<State, Event> {

    @Override
    public State next(State state, Event event, Object context) {
        if (State.pending_active.equals(state) && Event.recharge.equals(event)) {
            
            if (null == context) {
                // context == null 表示5天内充值不满3000元
                return state;
            }
            
            return State.active;
        }
        
        if (State.pending_active.equals(state) && Event.no_action.equals(event)) {
            return State.lost;
        }
        
        if (State.active.equals(state) && Event.pay.equals(event)) {
            
            if (null == context) {
                // context == null 表示30天以前就处于激活状态了
                return state;
            }
            
            return State.online;
        }
        
        if (State.pending_active.equals(state) && Event.no_action.equals(event)) {
            return State.lost;
        }
        
        if (State.online.equals(state) && Event.no_action.equals(event)) {
            return State.lost;
        }
        
        if (State.lost.equals(state) && Event.pay.equals(event)) {
            return State.online;
        }
        
        if (State.lost.equals(state) && Event.recharge.equals(event)) {
            return State.online;
        }
        
        throw new IllegalStateException();
    }

}
