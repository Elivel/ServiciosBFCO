package tech.falabella.qa.tuple;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
public abstract class Tuple implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected abstract JsonObject getId();

    public String toString() {
        var gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public String diff(Tuple other) {
        var jsonElement = new JsonObject();
        jsonElement.add("id", getId());
        jsonElement.addProperty("expected", this.toString());
        jsonElement.addProperty("actual", other.toString());

        var gson = new GsonBuilder().create();
        return gson.toJson(jsonElement);
    }
}