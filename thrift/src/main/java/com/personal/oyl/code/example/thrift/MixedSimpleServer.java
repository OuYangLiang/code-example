package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class MixedSimpleServer {
    public static void main(String[] args)  {
        HelloService.Processor<HelloServiceImpl> processor1 = 
                new HelloService.Processor<>(new HelloServiceImpl());
        
        AnotherService.Processor<AnotherServiceImpl> processor2 = 
                new AnotherService.Processor<>(new AnotherServiceImpl());
        
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        processor.registerProcessor("hello", processor1);
        processor.registerProcessor("another", processor2);
        
        try {
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
