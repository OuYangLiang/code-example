package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class MixedSimpleClient {

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 8080);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            
            HelloService.Client helloClient = new HelloService.Client(new TMultiplexedProtocol(protocol, "hello"));
            AnotherService.Client anotherClient = new AnotherService.Client(new TMultiplexedProtocol(protocol, "another"));

            System.out.println(helloClient.add(100, 200));
            System.out.println(anotherClient.hello("欧阳亮"));

            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}
