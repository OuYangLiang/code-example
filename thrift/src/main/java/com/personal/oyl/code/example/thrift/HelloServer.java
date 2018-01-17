package com.personal.oyl.code.example.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class HelloServer {
    private static HelloServiceImpl handler;
    private static HelloService.Processor<HelloServiceImpl> processor;
    
    public static void start(HelloService.Processor<HelloServiceImpl> processor){
        try {
            TServerTransport serverTransport = new TServerSocket(8080);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        handler = new HelloServiceImpl();
        processor = new HelloService.Processor<>(handler);
        start(processor);
    }
}
