package tech.falabella.qa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor(staticName = "newInstance")
public class ValidationServiceImpl implements ValidationService {

    private ReportPort reportInput;
    private ReportPort reportPersistence;


    @Override
    public void setInputData(ReportPort reportPort) {
        this.reportInput = reportPort;
    }

    @Override
    public void setPersistenceData(ReportPort reportPort) {
        this.reportPersistence = reportPort;
    }

    @Override
    public Collection processElements() {
        reportInput.generate();
        reportPersistence.generate();

        var persistenceData = reportPersistence.getData();
        var inputData = reportInput.getData();

        return persistenceData.parallelStream()
                .filter(it -> !inputData.contains(it))
                .toList();
    }
}
