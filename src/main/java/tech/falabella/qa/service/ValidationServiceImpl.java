package tech.falabella.qa.service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import tech.falabella.qa.dto.Result;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Map<JsonObject, Tuple> inputMap = inputData.stream()
                .collect(Collectors.toMap(Tuple::getId, tuple -> tuple));

        var inconsistencies = persistenceData.parallelStream()
                .map(persistenceElement -> {
                    Tuple matchingInput = inputMap.get(persistenceElement.getId());
                    if (matchingInput != null) {
                        if (!persistenceElement.equals(matchingInput)) {
                            return persistenceElement.diff(matchingInput);
                        }
                    } else {
                        return Tuple.result(persistenceElement.getId(), persistenceElement, null);
                    }
                    return null; // No hay inconsistencia para este elemento de persistencia
                })
                .filter(Objects::nonNull)
                .toList();

        // Identificar elementos solo en inputData
        var onlyInInput = inputData.parallelStream()
                .filter(inputElement -> persistenceData.stream()
                        .noneMatch(persistenceElement -> persistenceElement.getId().equals(inputElement.getId())))
                .map(inputElement -> Tuple.result(inputElement.getId(), null, inputElement))
                .toList();

        return new Result(inputData.size(), persistenceData.size(),
                Stream.concat(inconsistencies.stream(), onlyInInput.stream()).toList());

    }

}
