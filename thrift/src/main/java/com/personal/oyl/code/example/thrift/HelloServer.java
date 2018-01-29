package com.personal.oyl.code.example.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class HelloServer {
    public static void main(String[] args) {
        try {
            HelloService.Processor<HelloServiceImpl> processor = 
                    new HelloService.Processor<>(new HelloServiceImpl());
            
            TServerTransport serverTransport = new TServerSocket(8080);
            TServer.Args param = new TServer.Args(serverTransport);
            param.processor(processor);
            param.protocolFactory(new TBinaryProtocol.Factory());
            
            TServer server = new TSimpleServer(param);
            System.out.println("Starting Thrift Server......");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
