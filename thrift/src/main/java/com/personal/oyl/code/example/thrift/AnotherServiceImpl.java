package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TException;

public class AnotherServiceImpl implements AnotherService.Iface {

    @Override
    public String hello(String name) throws TException {
        return "Hello " + name;
    }

}
