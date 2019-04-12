package com.personal.oyl.myb.statemachine;

public final class StateMachineFactory {
    private static StateMachine<State, Event> individual;
    private static StateMachine<State, Event> enterprise;
    
    public static StateMachine<State, Event> getMachine(MemberType type) {
        
        if (MemberType.individual.equals(type)) {
            if (null == individual) {
                synchronized (StateMachineFactory.class) {
                    if (null == individual) {
                        individual = new IndividualStateMachine();
                    }
                }
            }
            
            return individual;
        }
        
        if (null == enterprise) {
            synchronized (StateMachineFactory.class) {
                if (null == enterprise) {
                    enterprise = new EnterpriseStateMachine();
                }
            }
        }
        
        return enterprise;
        
    }
}
