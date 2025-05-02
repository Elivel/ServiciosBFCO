package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void newInstance() {
        assertEquals(BigDecimal.valueOf(1_23400, 2), Money.from("$ 1.234,00 COP").value());
        assertEquals(BigDecimal.valueOf(5_67890, 2), Money.from("€ 5678.90").value());
        assertEquals(BigDecimal.valueOf(1_23456, 2), Money.from("1234.5560").value());
        assertEquals(BigDecimal.valueOf(-5_67890, 2), Money.from("- 5678.90").value());
        assertEquals(BigDecimal.valueOf(1_23456, 2), Money.from("1,234.5560").value());
        assertEquals(BigDecimal.valueOf(297_754_92418L, 2), Money.from("$297,754,924.18").value());
        assertEquals(BigDecimal.valueOf(-5_67890, 2), Money.from("- 5,678.90").value());
        assertNull(Money.from("AB.C").value());
        assertNull(Money.from(null).value());
    }

}