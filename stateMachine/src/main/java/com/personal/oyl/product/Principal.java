package com.personal.oyl.product;

public interface Principal<S extends Enum<S>, A extends Enum<A>> {
    void setState(S s);

    S getState();

    StateMachine<S, A> machine();
}


