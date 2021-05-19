package com.grpc.log.api;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/unary")
    public String greet() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        GreetServiceGrpc.GreetServiceBlockingStub greetServiceClient = GreetServiceGrpc.newBlockingStub(channel);
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

    @GetMapping("/server_streaming")
    public String greetManyTimes() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        GreetServiceGrpc.GreetServiceBlockingStub greetServiceClient = GreetServiceGrpc.newBlockingStub(channel);
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Mrinmay")
                .setLastName("Santra")
                .build();
        // Creating greeting request
        GreetManyTimesRequest request = GreetManyTimesRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        // Calling greeting service
        greetServiceClient.greetManyTimes(request).forEachRemaining(greetManyTimesResponse -> {
            System.out.println(greetManyTimesResponse.getResult());
        });
        // Stopping channel
        channel.shutdown();
        return "SUCCESS";
    }
}
