package tech.falabella.qa.service;

import tech.falabella.qa.adapter.ReportPort;
import tech.falabella.qa.report.Tuple;

import java.util.Collection;

public interface ValidationService<T extends Tuple> {

    void setInputData(ReportPort reportPort);

    void setPersistenceData(ReportPort reportPort);

    Collection<T> processElements();
}
