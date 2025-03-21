package tech.falabella.qa.report;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public interface ReportConfig<T extends Tuple> {

    Map<String, Integer> getParameters();

    String getRoute();

    String getQuery();

    T sqlMap(ResultSet resultSet);

    T csvMap(String[] result);

    default Map<Integer, String> validateAndGet(Map<String, String> dValues) {
        var result = new HashMap<Integer, String>();

        getParameters().forEach((paramName, paramIndex) ->
                result.put(paramIndex, dValues.get(paramName)));

        return result;
    }

}
