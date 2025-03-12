package tech.falabella.qa;

import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;

public interface ValidationService<T extends Tuple> {

    void setInputData(ReportPort reportPort);

    void setPersistenceData(ReportPort reportPort);

    Collection<T> processElements();
}
