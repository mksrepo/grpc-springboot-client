package com.grpc.log.controller;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController

public class RestApi {
    @GetMapping
    public String greeting() {
        // Getting channel instance
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        // Getting client
        GreetServiceGrpc.GreetServiceBlockingStub greetServiceClient = GreetServiceGrpc.newBlockingStub(channel);
        // Calling service call
        // Creating greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Mrinmay")
                .setLastName("Santra")
                .build();
        // Creating greeting request
        GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        // Calling greeting service
        GreetResponse response = greetServiceClient.greet(request);
        // Printing service response
        System.out.println(response.getResult());
        // Stopping channel
        channel.shutdown();
        return "SUCCESS";
    }
}