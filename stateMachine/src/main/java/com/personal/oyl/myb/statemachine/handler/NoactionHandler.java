package com.personal.oyl.myb.statemachine.handler;

import com.personal.oyl.myb.statemachine.AbstractStateHandler;
import com.personal.oyl.myb.statemachine.Event;
import com.personal.oyl.myb.statemachine.Member;

public class NoactionHandler extends AbstractStateHandler {

    @Override
    public Event event() {
        return Event.no_action;
    }

    @Override
    protected Object doHandle(Member member) {
        System.out.println("客户什么都没做，没充值，也没有支付");
        return null;
    }

}
