package tech.falabella.qa.service;

import tech.falabella.qa.report.Tuple;

import java.util.Collection;

public interface ValidationService {

    Collection<Tuple> processElements();
}
