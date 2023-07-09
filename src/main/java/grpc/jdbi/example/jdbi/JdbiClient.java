package grpc.jdbi.example.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

import javax.sql.DataSource;

public class JdbiClient {
    private static JdbiClient instance;
    private final Jdbi jdbi;

    private JdbiClient(JdbiDataSource dataSourceStrategy) {
        DataSource dataSource = dataSourceStrategy.getDataSource();
        this.jdbi = Jdbi.create(dataSource).installPlugin(new PostgresPlugin());
        jdbi.installPlugins();
    }

    public static synchronized JdbiClient getInstance(JdbiDataSource dataSourceStrategy) {
        if (instance == null) {
            instance = new JdbiClient(dataSourceStrategy);
        }
        return instance;
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
}
