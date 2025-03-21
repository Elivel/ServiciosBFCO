package tech.falabella.qa;

import org.junit.jupiter.api.Test;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAITuple;
import tech.falabella.qa.report.Tuple;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void loadContext() {

    }

    @Test
    void printAsJsonAndComparativeTuple() {
        Tuple tuple0 = ConsultaCLAITuple.builder().validacion("CORRECTO").numeroAutorizacion("123456").build();
        Tuple tuple1 = ConsultaCLAITuple.builder().numeroAutorizacion("123456").validacion("CORRECTO").build();

        System.out.println(tuple0);
        System.out.println(tuple1);
        System.out.println(tuple0.equals(tuple1));

        assertEquals(tuple0, tuple1);
    }

    @Test
    void printDiffTwoTuples() {
        Tuple tuple0 = ConsultaCLAITuple.builder().validacion("CORRECTO").numeroAutorizacion("123456").tarjeta("1234").cuenta("*1234").build();
        Tuple tuple1 = ConsultaCLAITuple.builder().validacion("INCORRECTO").numeroAutorizacion("123456").tarjeta("1234").cuenta("*1234").build();

        var diff = tuple0.diff(tuple1);

        assertEquals("{\"id\":{\"numero-autorizacion\":\"123456\"},\"expected\":\"{\\\"validacion\\\":\\\"CORRECTO\\\",\\\"numeroAutorizacion\\\":\\\"123456\\\"}\",\"actual\":\"{\\\"validacion\\\":\\\"INCORRECTO\\\",\\\"numeroAutorizacion\\\":\\\"123456\\\"}\"}", diff);
    }

}