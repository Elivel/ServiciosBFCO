package tech.falabella.qa;

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

@Slf4j
@Getter
@RequiredArgsConstructor
public class DDBBReportAdapter<T extends Tuple> implements ReportPort {

    private Collection<T> data;

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
        //String connectionUrl = "jdbc:sqlserver://localhost;encrypt=true;database=AdventureWorks;integratedSecurity=true;";

        return DriverManager.getConnection(url, username, password);
    }
}
