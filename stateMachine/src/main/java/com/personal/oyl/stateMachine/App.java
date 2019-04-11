package com.personal.oyl.stateMachine;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Order order = new Order();
        
        StateMachineEngine.post(order, Event.pay);
        StateMachineEngine.post(order, Event.deliver);
        StateMachineEngine.post(order, Event.sign_for);
    }
}
