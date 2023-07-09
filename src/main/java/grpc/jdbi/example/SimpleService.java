package grpc.jdbi.example;

import grpc.jdbi.example.jdbi.JdbiClient;
import grpc.jdbi.example.stub.Apple;
import grpc.jdbi.example.stub.Empty;
import grpc.jdbi.example.stub.Orange;
import grpc.jdbi.example.stub.OrangeCollection;
import grpc.jdbi.example.stub.Result;
import grpc.jdbi.example.stub.SimpleGrpcResourceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

import java.util.List;

public class SimpleService extends SimpleGrpcResourceGrpc.SimpleGrpcResourceImplBase {

    private final Jdbi jdbi;
    private final SimpleDAO simpleDAO;

    public SimpleService(JdbiClient jdbiClient) {
        this.jdbi = jdbiClient.getJdbi();
        this.simpleDAO = jdbi.onDemand(SimpleDAO.class);
    }

    @Override
    public void addOrangeToBasket(Orange request, StreamObserver<Result> responseObserver) {
        try {

            Integer id = simpleDAO.insertOrange(request.getColor(), request.getWeight());
            responseObserver.onNext(Result.newBuilder().setId(Long.toString(id)).build());
            responseObserver.onCompleted();

        }catch(Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void addAppleToBasket(Apple request, StreamObserver<Result> responseObserver) {
        try {

            long id = simpleDAO.insertApple(request.getColor(), request.getWeight());
            responseObserver.onNext(Result.newBuilder().setId(Long.toString(id)).build());
            responseObserver.onCompleted();

        }catch(Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void fetchOranges(Empty request, StreamObserver<OrangeCollection> responseObserver) {
        try {

            List<Orange> oranges = simpleDAO.getAllOranges();
            responseObserver.onNext(OrangeCollection.newBuilder().addAllOranges(oranges).build());
            responseObserver.onCompleted();

        }catch(Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
