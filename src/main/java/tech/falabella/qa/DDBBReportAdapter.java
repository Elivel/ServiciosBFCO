package tech.falabella.qa;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.tuple.Tuple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;


/**
 * required: java.sql.Connection,java.lang.String,java.util.Map<java.lang.Integer,java.lang.String>,java.util.function.Function<java.sql.ResultSet,T>
 *   found:  java.util.Collection<T>,java.sql.Connection,java.lang.String,java.util.Map<java.lang.Integer,java.lang.String>,java.util.function.Function<java.sql.ResultSet,T>
 *
 * @param <T>
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class DDBBReportAdapter<T extends Tuple> implements ReportPort {

    private transient Collection<T> data;

    private final Connection connection;
    private final String query;
    private final Map<Integer, String> parameters;
    private final Function<ResultSet, T> aMapFun;

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
            log.error(exception.getMessage(), exception);
        }
    }

    public static Connection createConnection(String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(url, username, password);
    }
}
