package com.personal.oyl.myb.statemachine;

public class App {
    public static void main(String[] args) {
        System.out.println("企业会员");
        Member member = new Member(MemberType.enterprise);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.recharge);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.pay);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.no_action);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.pay);
        System.out.println("当前状态：" + member.getState().name());
        
        
        System.out.println("个人会员");
        member = new Member(MemberType.individual);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.pay);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.no_action);
        System.out.println("当前状态：" + member.getState().name());
        
        StateMachineEngine.post(member, Event.pay); 
        System.out.println("当前状态：" + member.getState().name());
    }
}
