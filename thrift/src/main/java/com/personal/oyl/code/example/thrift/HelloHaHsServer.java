package com.personal.oyl.code.example.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class HelloHaHsServer {
    public static void main(String[] args) {
        try {
            HelloService.Processor<HelloServiceImpl> processor = 
                    new HelloService.Processor<>(new HelloServiceImpl());
            
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(8080);
            THsHaServer.Args param = new THsHaServer.Args(serverTransport);
            param.processor(processor);
            param.protocolFactory(new TBinaryProtocol.Factory());
            param.transportFactory(new TFramedTransport.Factory());
            
            TServer server = new THsHaServer(param);
            System.out.println("Starting Half-Sync/Half-Async Thrift Server......");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
