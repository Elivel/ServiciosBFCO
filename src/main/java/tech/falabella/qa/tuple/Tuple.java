package tech.falabella.qa.tuple;

import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
public abstract class Tuple implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public String toString() {
        var gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

}