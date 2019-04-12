package com.personal.oyl.myb.statemachine;

public interface StateMachine<S extends Enum<S>, E extends Enum<E>> {
    S next(S state, E event, Object context);
}
