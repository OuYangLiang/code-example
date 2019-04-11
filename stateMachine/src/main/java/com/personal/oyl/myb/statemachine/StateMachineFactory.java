package com.personal.oyl.myb.statemachine;

public final class StateMachineFactory {
    private static StateMachine individual;
    private static StateMachine enterprise;
    
    public static StateMachine getMachine(MemberType type) {
        
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
