package grpc.jdbi.example;

import grpc.jdbi.example.stub.Orange;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface SimpleDAO {

    @SqlUpdate("INSERT INTO orange (color, weight) VALUES (:color, :weight)")
    @SingleValue
    int insertOrange(@Bind("color") String color, @Bind("weight") int weight);

    @SqlUpdate("INSERT INTO apple (color, weight) VALUES (:color, :weight)")
    @SingleValue
    int insertApple(@Bind("color") String color, @Bind("weight") int weight);

    @SqlQuery("SELECT * FROM orange")
    @RegisterRowMapper(OrangeMapper.class)
    List<Orange> getAllOranges();
}
