package grpc.jdbi.example.jdbi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceStrategy implements JdbiDataSource {

    @Override
    public DataSource getDataSource() {
        // TODO inject this hardcode values by a config management
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
        config.setUsername("user");
        config.setPassword("topsecret");
        config.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(config);
    }
}
