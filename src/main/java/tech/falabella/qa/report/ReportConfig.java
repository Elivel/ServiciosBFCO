package tech.falabella.qa.report;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface ReportConfig<T extends Tuple> {

    Parameters getParameters();

    String getRoute();

    String getQuery();

    T sqlMap(ResultSet resultSet);

    T csvMap(String[] result);

    default Map<Integer, String> validateAndGet(Map<String, String> dValues) {
        var result = new HashMap<Integer, String>();

        getParameters().forEach((name, value) -> {
            var aValueByParamName = Optional
                    .ofNullable(dValues.get(name))
                    .map(it -> value.sqlFormat.apply(it))
                    .orElse(value.defaultValue);

            result.put(value.position, aValueByParamName);
        });

        return result;
    }

    default Map<String, Parameters.Value> assignDefaultValuesAndGet(Map<String, String> dValues) {
        var result = new HashMap<String, Parameters.Value>();

        getParameters().forEach((name, value) -> {
            value.defaultValue = Optional
                    .ofNullable(dValues.get(name))
                    .map(it -> value.action.apply(it))
                    .orElse(value.defaultValue);

            result.put(name, value);
        });
        return result;
    }
}
