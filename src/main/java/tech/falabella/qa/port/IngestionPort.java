package tech.falabella.qa.port;

import tech.falabella.qa.report.Tuple;

import java.util.Collection;

public interface IngestionPort {

    void generate();

    <T extends Tuple> Collection<T> getData();

}
