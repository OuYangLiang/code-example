package com.personal.oyl.stateMachine;

public interface StateHandler {
    void handle(Order order);
    
    Event event();
}
