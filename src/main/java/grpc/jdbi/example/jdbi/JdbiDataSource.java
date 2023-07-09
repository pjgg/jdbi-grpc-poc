package grpc.jdbi.example.jdbi;

import javax.sql.DataSource;


public interface JdbiDataSource {
    DataSource getDataSource();
}
