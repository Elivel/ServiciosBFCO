package tech.falabella.qa.report;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.io.IOException;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new TypeAdapter<DateTime>() {
            @Override
            public void write(JsonWriter jsonWriter, DateTime value) throws IOException {
                if (value == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value(value.value().toString());
                }
            }
            @Override
            public DateTime read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == com.google.gson.stream.JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return DateTime.from(jsonReader.nextString());
            }
        });
        gsonBuilder.registerTypeAdapter(Money.class, new TypeAdapter<Money>() {
            @Override
            public void write(JsonWriter jsonWriter, Money money) throws IOException {
                if (money == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value(money.value().toString());
                }
            }
            @Override
            public Money read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == com.google.gson.stream.JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Money.from(jsonReader.nextString());
            }
        });
        gsonBuilder.registerTypeAdapter(Number.class, new TypeAdapter<Number>() {
            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value(number.value().toString());
                }
            }
            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == com.google.gson.stream.JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Number.from(jsonReader.nextString());
            }
        });

        return gsonBuilder.create().toJson(this);
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