package tech.falabella.qa;

import org.junit.jupiter.api.Test;
import tech.falabella.qa.tuple.ConsultaCLAITuple;
import tech.falabella.qa.tuple.Tuple;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void loadContext() {

    }

    @Test
    void printAndComparativeTuple() {
        Tuple tuple0 = ConsultaCLAITuple.builder().validacion("CORRECTO").numeroAutorizacion("123456").build();
        Tuple tuple1 = ConsultaCLAITuple.builder().numeroAutorizacion("123456").validacion("INCORRECTO").build();
        System.out.println(tuple0);
        System.out.println(tuple1);
        System.out.println(tuple0.equals(tuple1));

        assertEquals(tuple0, tuple1);
    }

}