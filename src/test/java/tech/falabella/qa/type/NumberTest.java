package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void newInstance() {
        assertEquals(600, Number.from("00600").value());
        assertEquals(123, Number.from("123").value());
        assertEquals(0, Number.from("0").value());
        assertEquals(0, Number.from("000").value());
        assertEquals(0, Number.from("000").value());

        assertEquals(31_744.16f, Number.from("3174416.000%").value());
        assertEquals(31_744.16f, Number.from("31744.160000000000000000").value());

        assertEquals(null, Number.from(null).value());
        assertEquals(null, Number.from("abc").value());
    }

}