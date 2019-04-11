package com.personal.oyl.stateMachine;

public interface StateMachine {
    State next(State state, Event event);
}
