package com.personal.oyl.myb.statemachine;

public interface StateMachine {
    State next(State state, Event event, Object context);
}
