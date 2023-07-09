package grpc.jdbi.example;

import grpc.jdbi.example.stub.Orange;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class OrangeMapper implements RowMapper<Orange> {

    @Override
    public Orange map(ResultSet rs, StatementContext ctx) throws SQLException {

        if(Objects.isNull(rs)) {
            throw new IllegalArgumentException(String.format("'ResultSet' must not be null!"));
        }

        return Orange.newBuilder()
                .setColor(rs.getNString("color"))
                .setWeight(rs.getInt("weight"))
                .build();
    }
}
