package tech.falabella.qa.service;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.dto.Result;
import tech.falabella.qa.port.IngestionPort;

@RequiredArgsConstructor(staticName = "newInstance")
public class ValidationServiceImpl implements ValidationService {

    private final IngestionPort input;
    private final IngestionPort persistence;

    @Override
    public Result processElements() {
        input.generate();
        persistence.generate();

        var inputData = input.getData();
        var persistenceData = persistence.getData();

        return new Result(inputData.size(), persistenceData.size(),
                persistenceData.parallelStream()
                        .filter(it -> !inputData.contains(it))
                        .map(it ->
                                inputData.stream()
                                        .filter(o -> o.getId().equals(it.getId()))
                                        .findFirst()
                                        .map(it::diff)
                                        .orElse("")
                        )
                        .toList());

    }

}
