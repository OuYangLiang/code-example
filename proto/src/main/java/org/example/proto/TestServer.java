package org.example.proto;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author OuYang Liang
 * @since 2020-05-19
 */
public class TestServer {

    private final Server server;

    public TestServer(int port) {
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build();
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        TestServer testServer = new TestServer(9999);
        testServer.start();
    }
}
