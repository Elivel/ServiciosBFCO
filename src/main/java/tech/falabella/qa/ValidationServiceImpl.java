package tech.falabella.qa;

import lombok.NoArgsConstructor;
import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(staticName = "newInstance")
public class ValidationServiceImpl implements ValidationService {

    private ReportPort reportInput;
    private ReportPort reportPersistence;

    @Override
    public <T extends Tuple> Collection<T> getInputData() {
        return reportInput.getData();
    }

    @Override
    public <T extends Tuple> Collection<T> getPersistenceData() {
        return List.of();
    }
}
