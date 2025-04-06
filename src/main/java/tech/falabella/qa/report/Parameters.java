package tech.falabella.qa.report;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of")
public class Parameters {
    private Map<String, Value> value;

    public String[] getKeys() {
        return this.value.keySet().toArray(String[]::new);
    }

    public void forEach(BiConsumer<String, Value> action) {
        Objects.requireNonNull(action);
        for (var entry : value.entrySet()) {
            String key;
            Value val;
            try {
                key = entry.getKey();
                val = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            action.accept(key, val);
        }
    }

    @Builder
    @RequiredArgsConstructor(staticName = "of")
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Value {
        public final Integer position;
        public String pattern;
        @Builder.Default
        public String defaultValue = "";
        public Function<String, String> action = (dValue) -> dValue;
    }


}
