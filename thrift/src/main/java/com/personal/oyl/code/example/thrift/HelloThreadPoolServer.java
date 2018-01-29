package com.personal.oyl.code.example.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class HelloThreadPoolServer {
    public static void main(String[] args) {
        try {
            HelloService.Processor<HelloServiceImpl> processor = 
                    new HelloService.Processor<>(new HelloServiceImpl());
            
            TServerTransport serverTransport = new TServerSocket(8080);
            TThreadPoolServer.Args param = new TThreadPoolServer.Args(serverTransport);
            param.processor(processor);
            param.protocolFactory(new TBinaryProtocol.Factory());
            
            TServer server = new TThreadPoolServer(param);
            System.out.println("Starting ThreadPool Thrift Server......");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
