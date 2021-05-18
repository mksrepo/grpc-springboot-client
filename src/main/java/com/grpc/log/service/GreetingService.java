package com.grpc.log.service;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreetingService extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // extracting value from request
        Greeting greeting = request.getGreeting();
        final String firstName = greeting.getFirstName();
        final String lastName = greeting.getLastName();
        // creating response
        String result = "Hello " + firstName + " " + lastName;
        GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
        // send the response
        responseObserver.onNext(response);
        // complete the RPC call
        responseObserver.onCompleted();
    }
}