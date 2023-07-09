package grpc.jdbi.example;

import grpc.jdbi.example.jdbi.HikariDataSourceStrategy;
import grpc.jdbi.example.jdbi.JdbiClient;
import grpc.jdbi.example.jdbi.JdbiDataSource;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(8080)
                .addService(new SimpleService(defaultJdbiClient()))
                .build();

        server.start();

        System.out.println("Server started, listening on port " + server.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server gracefully...");
            server.shutdown();
        }));

        server.awaitTermination();
    }

    private static JdbiClient defaultJdbiClient() {
        JdbiDataSource dataSourceStrategy = new HikariDataSourceStrategy();
        return JdbiClient.getInstance(dataSourceStrategy);
    }
}
