package com.personal.oyl.myb.statemachine;

public interface StateHandler {
    void handle(Member member);
    
    Event event();
}
