package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeTest {

    @Test
    void newInstance() {
        var dt1 = DateTime.from("2023-10-26T10:30:00");
        var dt2 = DateTime.from("26/10/2023 14:45:30");
        var dt3 = DateTime.from("2024-10-15");
        var dt4 = DateTime.from("2024/10/15");
        var dt5 = DateTime.from("invalid-format");

        assertEquals(LocalDateTime.of(2023, 10, 26, 10, 30, 0), dt1.value());
        assertEquals(LocalDateTime.of(2023, 10, 26, 14, 45, 30), dt2.value());
        assertEquals(LocalDate.of(2024, 10, 15), dt3.value());
        assertEquals(LocalDate.of(2024, 10, 15), dt4.value());
        assertNull(dt5.value());
    }
}