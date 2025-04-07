package tech.falabella.qa.service;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.port.IngestionPort;

import java.util.Collection;

@RequiredArgsConstructor(staticName = "newInstance")
public class ValidationServiceImpl implements ValidationService {

    private final IngestionPort input;
    private final IngestionPort persistence;

    @Override
    public Collection<String> processElements() {
        input.generate();
        persistence.generate();

        var inputData = input.getData();
        var persistenceData = persistence.getData();

        return persistenceData.parallelStream()
                .filter(it -> !inputData.contains(it))
                .map(it -> {
                    var other = inputData.stream().filter(o -> o.getId().equals(it.getId())).findFirst().get();
                    return it.diff(other);
                })
                .toList();
    }

}
