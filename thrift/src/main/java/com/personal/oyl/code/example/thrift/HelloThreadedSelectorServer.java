package com.personal.oyl.code.example.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class HelloThreadedSelectorServer {
    public static void main(String[] args) {
        try {
            HelloService.Processor<HelloServiceImpl> processor = 
                    new HelloService.Processor<>(new HelloServiceImpl());
            
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(8080);
            TThreadedSelectorServer.Args param = new TThreadedSelectorServer.Args(serverTransport);
            param.processor(processor);
            param.protocolFactory(new TBinaryProtocol.Factory());
            param.transportFactory(new TFramedTransport.Factory());
            
            TServer server = new TThreadedSelectorServer(param);
            System.out.println("Starting ThreadedSelector Thrift Server......");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
