package com.personal.oyl.stateMachine;

public abstract class AbstractStateHandler implements StateHandler {

    @Override
    public void handle(Order order) {
        this.before(order);
        this.doHandler(order);
        order.setState(StateMachineFactory.getMachine().next(order.getState(), this.event()));
        this.after(order);
        
        
    }

    protected void before(Order order) {
        
    }
    
    protected void after(Order order) {
        
    }
    
    protected abstract void doHandler(Order order);
    
}
