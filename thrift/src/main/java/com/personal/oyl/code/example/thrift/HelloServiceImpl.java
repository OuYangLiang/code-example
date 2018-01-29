package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TException;

public class HelloServiceImpl implements HelloService.Iface{

    @Override
    public int add(int num1, int num2) throws TException {
        return num1 + num2;
    }

}
