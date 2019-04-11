package com.personal.oyl.myb.statemachine;

public class Member {
    private MemberType type;
    private State state;
    
    public Member(MemberType type) {
        this.setType(type);
        this.setState(State.pending_active);
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
