package grpc.jdbi.example.toolset;

import grpc.jdbi.example.stub.Basket;
import grpc.jdbi.example.stub.Empty;
import grpc.jdbi.example.stub.Orange;
import grpc.jdbi.example.stub.SimpleGrpcResourceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class SimpleServiceClient {

    private final SimpleGrpcResourceGrpc.SimpleGrpcResourceBlockingStub simpleGrpcCli;

    public SimpleServiceClient(int internalApiPort) {
        final ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", internalApiPort);
        channelBuilder.usePlaintext();
        final ManagedChannel channel = channelBuilder.build();
        this.simpleGrpcCli = SimpleGrpcResourceGrpc.newBlockingStub(channel);
    }

    public List<Orange> getAllOranges() {
        return simpleGrpcCli.fetchOranges(Empty.newBuilder().build()).getOrangesList();
    }

    public String addOrange(Orange orange)  {
        return simpleGrpcCli.addOrangeToBasket(orange).getId();
    }

    public void addBasket(Basket basket) {
        simpleGrpcCli.addAppleAndOrangeToBasket(basket);
    }
}
