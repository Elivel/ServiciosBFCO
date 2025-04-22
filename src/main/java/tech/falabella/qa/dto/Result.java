package tech.falabella.qa.dto;

import java.util.Collection;

public record Result(long inputCount, long persistenceCount, Collection<String> inconsistencies) {

    public String status() {
        if (inputCount != persistenceCount) return "Fallido";

        return inconsistencies.isEmpty() ? "Exitoso" : "Fallido";
    }

    public long inconsistenciesCount() {
        return inconsistencies.size();
    }
}
