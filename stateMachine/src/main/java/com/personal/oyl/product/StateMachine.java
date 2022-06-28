package com.personal.oyl.product;

public interface StateMachine<S extends Enum<S>, A extends Enum<A>> {
    S next(S state, A action, Object context);
}
