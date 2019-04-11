package com.personal.oyl.myb.statemachine.handler;

import com.personal.oyl.myb.statemachine.AbstractStateHandler;
import com.personal.oyl.myb.statemachine.Event;
import com.personal.oyl.myb.statemachine.Member;

public class EnterprisePayHandler extends AbstractStateHandler {

    @Override
    public Event event() {
        return Event.pay;
    }

    @Override
    protected Object doHandle(Member member) {
        System.out.println("30天内支付了");
        return new Object();
    }

}
