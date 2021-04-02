package org.example.proto;

import com.ymm.crm.example.GreeterGrpc;
import com.ymm.crm.example.GreeterService;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2020-05-19
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterService.HelloRequest request, StreamObserver<GreeterService.HelloReply> responseObserver) {
        responseObserver.onNext(GreeterService.HelloReply.newBuilder().setMessage("Hello, " + request.getName()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void sayHelloWithServerStreaming(GreeterService.HelloRequest request, StreamObserver<GreeterService.HelloReply> responseObserver) {
        responseObserver.onNext(GreeterService.HelloReply.newBuilder().setMessage("Hi, " + request.getName()).build());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(GreeterService.HelloReply.newBuilder().setMessage("Hi 2, " + request.getName()).build());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(GreeterService.HelloReply.newBuilder().setMessage("Hi 3, " + request.getName()).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GreeterService.HelloRequest> sayHelloWithClientStreaming(StreamObserver<GreeterService.HelloReply> responseObserver) {
        return new StreamObserver<GreeterService.HelloRequest>() {
            private String name = "";
            @Override
            public void onNext(GreeterService.HelloRequest helloRequest) {
                this.name = name + helloRequest.getName();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(GreeterService.HelloReply.newBuilder().setMessage(name).build());
                responseObserver.onCompleted();
            }
        };
    }
}
