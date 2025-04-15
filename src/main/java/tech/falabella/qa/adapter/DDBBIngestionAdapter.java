package tech.falabella.qa.adapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.exception.GenerateIngestionObjectException;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
@RequiredArgsConstructor
public class DDBBIngestionAdapter<T extends Tuple> implements IngestionPort {

    private final Connection connection;
    private final String query;
    private final Map<Integer, String> parameters;
    private final Function<ResultSet, T> aMapFun;
    private transient Collection<T> data;

    public static Connection createConnection(String url, String username, String password) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new GenerateIngestionObjectException("Error estableciendo conexión a base de datos");
        }
    }

    @Override
    public void generate() {
        try {
            var statement = connection.prepareStatement(query);

            for (var entry : parameters.entrySet()) {
                statement.setString(entry.getKey(), entry.getValue());
            }

            var resultSet = statement.executeQuery();

            data = new ArrayList<>();
            while (resultSet.next()) {
                data.add(aMapFun.apply(resultSet));
            }
        } catch (SQLException exception) {
            throw new GenerateIngestionObjectException(exception.getMessage());
        }
    }
}
