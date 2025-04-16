package tech.falabella.qa.report;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor
public abstract class Tuple implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected transient Function<Map<String, String>, JsonObject> toJsonIds = fields -> {
        var ids = new JsonObject();
        fields.forEach((key, value) ->
                ids.add(key, new JsonPrimitive(value))
        );
        return ids;
    };

    public abstract JsonObject getId();

    public String toString() {
        var gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public String diff(Tuple other) {
        var gson = new GsonBuilder().create();

        var jsonElement = new JsonObject();
        jsonElement.add("id", getId());
        jsonElement.add("expected", gson.toJsonTree(this));
        jsonElement.add("actual", gson.toJsonTree(other));

        return gson.toJson(jsonElement);
    }
}