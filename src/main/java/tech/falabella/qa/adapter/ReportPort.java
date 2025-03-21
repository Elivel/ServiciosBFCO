package tech.falabella.qa.adapter;

import tech.falabella.qa.report.Tuple;

import java.util.Collection;

public interface ReportPort {

    void generate();

    <T extends Tuple> Collection<T> getData();

}
