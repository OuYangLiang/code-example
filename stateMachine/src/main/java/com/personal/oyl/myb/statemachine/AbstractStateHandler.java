package com.personal.oyl.myb.statemachine;

public abstract class AbstractStateHandler implements StateHandler {

    @Override
    public final void handle(Member member) {
        this.before(member);
        Object context = this.doHandle(member);
        member.setState(StateMachineFactory.getMachine(member.getType())
                .next(member.getState(), this.event(), context));
        this.after(member);
    }

    protected void before(Member member) {
        
    }
    
    protected void after(Member member) {
        
    }
    
    protected abstract Object doHandle(Member member);
    
}
