package tech.falabella.qa.type;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void newInstance() {
        String monedaFuente1 = "$ 1234,00 COP";
        String monedaFuente2 = "1234.56";
        String monedaFuente3 = "€ 5678.90";
        String monedaFuenteInvalida = "AB.C";
        String monedaFuenteVacia = null;

        var enmascarado1 = Money.from(monedaFuente1);
        var enmascarado2 = Money.from(monedaFuente2);
        var enmascarado3 = Money.from(monedaFuente3);
        var enmascaradoInvalida = Money.from(monedaFuenteInvalida);
        var enmascaradoVacia = Money.from(monedaFuenteVacia);

        assertEquals(BigDecimal.valueOf(1_23400, 2), enmascarado1.value());
        assertEquals(BigDecimal.valueOf(1_234.56), enmascarado2.value());
        assertEquals(BigDecimal.valueOf(5_67890, 2), enmascarado3.value());
        assertNull(enmascaradoInvalida.value());
        assertNull(enmascaradoVacia.value());
    }

}