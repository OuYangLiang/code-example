package org.example.proto;

import com.ymm.crm.example.GreeterGrpc;
import com.ymm.crm.example.GreeterService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2020-05-19
 */
public class TestClient {

    private static void rpc(GreeterGrpc.GreeterBlockingStub blockingStub) {
        GreeterService.HelloReply reply = blockingStub.sayHello(GreeterService.HelloRequest.newBuilder().setName("张三").build());
        System.out.println(reply.getMessage());
    }

    private static void rpcWithServerStream(GreeterGrpc.GreeterStub asyncStub) {
        asyncStub.sayHelloWithServerStreaming(GreeterService.HelloRequest.newBuilder().setName("李四").build(), new StreamObserver<GreeterService.HelloReply>(){

            @Override
            public void onNext(GreeterService.HelloReply helloReply) {
                System.out.println(helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Finished...");
            }
        });
    }

    private static void rpcWichClientStream(GreeterGrpc.GreeterStub asyncStub) {
        StreamObserver<GreeterService.HelloReply> resp = new StreamObserver<GreeterService.HelloReply>() {
            @Override
            public void onNext(GreeterService.HelloReply helloReply) {
                System.out.println(helloReply.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Over");
            }
        };

        StreamObserver<GreeterService.HelloRequest> req = asyncStub.sayHelloWithClientStreaming(resp);

        req.onNext(GreeterService.HelloRequest.newBuilder().setName("张三").build());
        req.onNext(GreeterService.HelloRequest.newBuilder().setName("&").build());
        req.onNext(GreeterService.HelloRequest.newBuilder().setName("李四").build());

        req.onCompleted();
    }
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext().build();
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);
        GreeterGrpc.GreeterStub asyncStub = GreeterGrpc.newStub(channel);

        rpc(blockingStub);
        rpcWithServerStream(asyncStub);
        rpcWichClientStream(asyncStub);

        try {
            TimeUnit.SECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
