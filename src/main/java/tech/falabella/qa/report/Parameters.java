package tech.falabella.qa.report;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
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
        value.entrySet().stream()
                .sorted(Comparator.comparingInt(it -> it.getValue().position))
                .forEach(entry -> {
                    String key;
                    Value val;
                    try {
                        key = entry.getKey();
                        val = entry.getValue();
                    } catch (IllegalStateException ise) {
                        throw new ConcurrentModificationException(ise);
                    }
                    action.accept(key, val);
                });
        
    }

    @Builder
    @RequiredArgsConstructor(staticName = "of")
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Value {
        public final Integer position;
        public String defaultValue = "";
        @Builder.Default
        public String type = "input";
        public Function<String, String> action = (dValue) -> dValue;
    }


}
