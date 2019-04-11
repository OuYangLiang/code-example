package com.personal.oyl.myb.statemachine.handler;

import com.personal.oyl.myb.statemachine.AbstractStateHandler;
import com.personal.oyl.myb.statemachine.Event;
import com.personal.oyl.myb.statemachine.Member;

public class EnterpriseRechargeHandler extends AbstractStateHandler {

    @Override
    public Event event() {
        return Event.recharge;
    }

    @Override
    protected Object doHandle(Member member) {
        System.out.println("5天内充值了6000元");
        return new Object();
    }

}
