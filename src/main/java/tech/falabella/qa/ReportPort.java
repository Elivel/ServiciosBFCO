package tech.falabella.qa;

import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;

public interface ReportPort {

    void generate();

    <T extends Tuple> Collection<T> getData();

}
