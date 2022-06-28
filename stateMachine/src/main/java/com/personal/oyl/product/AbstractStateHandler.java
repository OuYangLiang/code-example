package com.personal.oyl.product;


public abstract class AbstractStateHandler<P extends Principal, A extends Enum<A>> implements StateHandler<P, A> {

    @Override
    public final void handle(P p) {
        this.before(p);
        Object context = this.doHandle(p);
        p.setState(p.machine().next(p.getState(), this.action(), context));
        this.after(p);
    }

    protected abstract Object doHandle(P p);
}
