package tech.falabella.qa.type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Locale;

public record DateTime(Temporal value) {

    private static final String[] dateTimeFormats = {
            "M/d/yyyy hh:mm:ss a",
            "M/dd/yyyy hh:mm:ss a",
            "MM/d/yyyy hh:mm:ss a",
            "MM/dd/yyyy hh:mm:ss a",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss",
            "dd-MM-yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy/MM/dd HH:mm:ss.SSS",
            "dd-MM-yyyy HH:mm:ss.SSS",
            "dd/MM/yyyy HH:mm:ss.SSS"
    };

    private static final String[] dateFormats = {
            "yyyy-MM-dd",
            "dd-MM-yyyy",
            "yyyy/MM/dd",
            "yyyyMMdd",
            "dd/MM/yyyy"
    };

    public static DateTime from(String unformatted) {
        if (null == unformatted || unformatted.isBlank())
            return new DateTime(null);

        unformatted = unformatted.trim();
        Temporal parsedDateTime = null;

        for (String format : dateTimeFormats) {
            try {
                var formatter = DateTimeFormatter.ofPattern(format, Locale.US);
                parsedDateTime = LocalDateTime.parse(unformatted, formatter);
                break;
            } catch (DateTimeParseException ignore) {
                // Ignore the exception and try the next format
            }
        }

        if (null == parsedDateTime)
            for (String format : dateFormats) {
                try {
                    var formatter = DateTimeFormatter.ofPattern(format);
                    parsedDateTime = LocalDate.parse(unformatted, formatter);
                    break;
                } catch (DateTimeParseException ignore) {
                    // Ignore the exception and try the next format
                }
            }

        return new DateTime(parsedDateTime);
    }
}
