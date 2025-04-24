package tech.falabella.qa.type;

import java.math.BigDecimal;
import java.util.Optional;

public record Number(Long value) {

    public static Number from(String unformatted) {
        return Optional.ofNullable(unformatted)
                .map(it -> it.replaceAll("[^0-9]", ""))
                .map(String::trim)
                .filter(it -> !it.isEmpty())
                .map(Long::parseLong)
                .map(Number::new)
                .orElseGet(() -> new Number(null));
/*
        if (unformatted == null || unformatted.isEmpty()) {
            return new Number(null); // O lanza una excepción, según tu necesidad
        }
        try {
            Long parsedValue = Long.parseLong(unformatted.replaceFirst("^0+(?!$)", ""));
            return new Number(parsedValue);
        } catch (NumberFormatException e) {
            // Maneja la excepción si la cadena no es un número válido
            System.err.println("Error al convertir la cadena a número: " + unformatted);
            return null; // O lanza una excepción, según tu necesidad
        }

 */
    }
}
