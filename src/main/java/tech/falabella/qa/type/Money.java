package tech.falabella.qa.type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.regex.Pattern;

public record Money(BigDecimal value) {

    private static Pattern pattern = Pattern.compile("^\\s*([$€])?\\s*([-+]?)?\\s*(?:(\\d{1,3}(?:[.,]\\d{3})*)|(\\d+))(?:([.,])(\\d+))?\\s*(?:COP)?\\s*$");

    public static Money from(String unformatted) {
        return Optional.ofNullable(unformatted)
                .map(it -> {
                    var matcher = pattern.matcher(it);

                    if (matcher.matches()) {
                        StringBuilder numberPart = new StringBuilder();
                        String sign = matcher.group(2); // Grupo del signo (- o +)
                        String integerPartWithSeparators1 = matcher.group(3); // Parte entera con separadores (punto o coma)
                        String integerPartWithoutSeparators = matcher.group(4); // Parte entera sin separadores
                        String decimalSeparator = matcher.group(5); // Separador decimal (punto o coma)
                        String decimalPart = matcher.group(6); // Parte decimal

                        if (sign != null && !sign.isEmpty()) {
                            numberPart.append(sign);
                        }

                        if (integerPartWithSeparators1 != null) {
                            numberPart.append(integerPartWithSeparators1.replaceAll("[.,]", ""));
                        } else if (integerPartWithoutSeparators != null) {
                            numberPart.append(integerPartWithoutSeparators);
                        }

                        if (decimalSeparator != null && decimalPart != null) {
                            numberPart.append(".").append(decimalPart);
                        }

                        return numberPart.toString();
                    }
                    return null;
                })
                .map(it -> it.replaceAll("[^0-9.-]", ""))
                .map(String::trim)
                .filter(it -> !it.isEmpty() && !it.equals(".") && !it.equals("-.") && !it.equals("-"))
                .map(BigDecimal::new)
                .map(it -> it.setScale(2, RoundingMode.HALF_UP))
                .map(Money::new)
                .orElseGet(() -> new Money(null));

    }

}
