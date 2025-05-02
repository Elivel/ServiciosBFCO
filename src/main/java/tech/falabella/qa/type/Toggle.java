package tech.falabella.qa.type;

import java.util.Optional;

public record Toggle(Boolean value) {

    public static Toggle from(String unformatted) {
        return Optional.ofNullable(unformatted)
                .map(String::trim)
                .map(it -> it.equalsIgnoreCase("1")
                        || it.equalsIgnoreCase("true")
                        || it.equalsIgnoreCase("t"))
                .map(Toggle::new)
                .orElse(new Toggle(null));
    }
}
