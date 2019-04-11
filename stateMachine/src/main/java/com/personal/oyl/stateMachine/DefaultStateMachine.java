package com.personal.oyl.stateMachine;

public class DefaultStateMachine implements StateMachine {

    @Override
    public State next(State state, Event event) {
        if (State.pending_payment.equals(state)) {
            return State.pending_delivery;
        }
        
        if (State.pending_delivery.equals(state)) {
            return State.pending_sign_for;
        }
        
        if (State.pending_sign_for.equals(state)) {
            return State.completed;
        }
        
        throw new IllegalStateException();
    }

}
