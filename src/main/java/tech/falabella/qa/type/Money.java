package tech.falabella.qa.type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public record Money(BigDecimal value) {

    public static Money from(String unformatted) {
        try {
            return Optional.ofNullable(unformatted)
                    .map(it -> {
                        if (it.contains(",")) {
                            return it.replaceAll("[.]", "")
                                    .replace(",", ".");
                        }
                        return it;
                    })
                    .map(it -> it.replaceAll("[^0-9.-]", ""))
                    .map(String::trim)
                    .filter(it -> !it.isEmpty() && !it.equals("."))
                    .map(BigDecimal::new)
                    .map(it -> it.setScale(2, RoundingMode.HALF_UP))
                    .map(Money::new)
                    .orElseGet(() -> new Money(null));
        } catch (Exception e) {
            return new Money(null);
        }
    }

}
