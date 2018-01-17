package com.personal.oyl.code.example.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloClient {
    private static HelloService.Client client;

    private static TTransport transport;

    /**
     * 创建 TTransport
     * 
     * @return
     */
    public static TTransport createTTransport() {
        TTransport transport = new TSocket("localhost", 8080);
        return transport;
    }

    /**
     * 开启 TTransport
     * 
     * @param transport
     * @throws TTransportException
     */
    public static void openTTransport(TTransport transport) throws TTransportException {
        transport.open();
    }

    /**
     * 关闭 TTransport
     * 
     * @param transport
     */
    public static void closeTTransport(TTransport transport) {
        transport.close();
    }

    /**
     * 创建客户端
     * 
     * @return
     */
    public static HelloService.Client createClient(TTransport transport) {
        TProtocol protocol = new TBinaryProtocol(transport);
        HelloService.Client client = new HelloService.Client(protocol);
        return client;
    }

    public static void main(String[] args) {
        try {
            // 创建 TTransport
            transport = createTTransport();
            // 开启 TTransport
            openTTransport(transport);
            // 创建客户端
            client = createClient(transport);
            System.out.println(client.add(100, 200));
            System.out.println(client.hello("John"));
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            // 关闭 TTransport
            closeTTransport(transport);
        }
    }
}
