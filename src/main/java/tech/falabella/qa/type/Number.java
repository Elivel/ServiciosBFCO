package tech.falabella.qa.type;

import java.util.Optional;

public record Number(Float value) {

    public static Number from(String unformatted) {
        return Optional.ofNullable(unformatted)
                .map(it -> it.replaceAll("[^0-9.]", ""))
                .map(String::trim)
                .filter(it -> !it.isEmpty())
                .map(Float::parseFloat)
                .map(it -> unformatted.contains("%") ? it / 100 : it)
                .map(Number::new)
                .orElseGet(() -> new Number(null));
    }
}
