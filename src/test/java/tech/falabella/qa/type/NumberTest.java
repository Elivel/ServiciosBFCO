package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    void newInstance() {
        var n1 = Number.from("00600");
        var n2 = Number.from("123");
        var n3 = Number.from("0");
        var n4 = Number.from("000");
        var n5 = Number.from(null);
        var n6 = Number.from("abc");

        assertEquals(600, n1.value());
        assertEquals(123, n2.value());
        assertEquals(0, n3.value());
        assertEquals(0, n4.value());
        assertEquals(null, n5.value());
        assertEquals(null, n6.value());
    }

}