package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToggleTest {
    @Test
    void newInstance() {
        assertTrue(Toggle.from("1").value());
        assertTrue(Toggle.from("true").value());
        assertTrue(Toggle.from("t").value());
        assertFalse(Toggle.from("0").value());
        assertFalse(Toggle.from("false").value());
        assertFalse(Toggle.from("f").value());
        assertFalse(Toggle.from("any-bad-value").value());
        assertNull(Toggle.from(null).value());
    }
}