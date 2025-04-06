package tech.falabella.qa.service;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.util.Collection;

@RequiredArgsConstructor(staticName = "newInstance")
public class ValidationServiceImpl implements ValidationService {

    private final IngestionPort input;
    private final IngestionPort persistence;

    @Override
    public Collection<Tuple> processElements() {
        input.generate();
        persistence.generate();

        var inputData = input.getData();
        var persistenceData = persistence.getData();

        return persistenceData.parallelStream()
                .filter(it -> !inputData.contains(it))
                .toList();
    }

}
