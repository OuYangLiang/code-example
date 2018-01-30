package com.personal.oyl.code.example.thrift;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

public class HelloAsyncClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket("localhost", 8080, 1000);

            HelloService.AsyncClient asyncClient = new HelloService.AsyncClient(new TBinaryProtocol.Factory(), clientManager, transport);
            CountDownLatch latch = new CountDownLatch(1);
            System.out.println("call method add start ...");
            asyncClient.add(100, 200, new AsynCallback(latch));
            System.out.println("call method add .... end");
            latch.await();
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
    
    public static class AsynCallback implements AsyncMethodCallback<Integer> {

        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(Integer response) {
            try {
                System.out.println("add result =:" + response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

}
