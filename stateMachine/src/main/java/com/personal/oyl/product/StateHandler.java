package com.personal.oyl.product;

public interface StateHandler<P, A extends Enum<A>> {
    void handle(P p);

    default void before(P p) {

    }

    default void after(P p) {

    }

    A action();
}
