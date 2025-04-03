package tech.falabella.qa.adapter;

import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.util.Collection;
import java.util.List;

public class ScrapingIngestionAdapter implements IngestionPort {

    @Override
    public void generate() {

    }

    @Override
    public <T extends Tuple> Collection<T> getData() {
        return List.of();
    }
}
