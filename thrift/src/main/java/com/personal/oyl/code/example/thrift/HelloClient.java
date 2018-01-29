package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HelloClient {

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 8080);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            HelloService.Client client = new HelloService.Client(protocol);

            System.out.println(client.add(100, 200));

            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
