package tech.falabella.qa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAITuple;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ValidationServiceImplTest {

    IngestionPort input = Mockito.mock(IngestionPort.class);
    IngestionPort persistence = Mockito.mock(IngestionPort.class);

    ValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = ValidationServiceImpl.newInstance(input, persistence);
    }

    @Test
    void processElementsConsultaClai() {
        // Arrange
        Collection<Tuple> inputList = List.of(
                newTuple(new String[] {"INCORRECTO","399300","*0130","300000940749","128,430","2024/09/08 16:13:30",  "9/9/2024 12:00:00 AM",  "9/9/2024 12:00:00 AM","0.00","0.00","0.00","13933601"}),
                newTuple(new String[] {"CORRECTO",  "399300","*5544","528209670035","730,000","2023/12/29 16:39:10","12/30/2023 12:00:00 AM","12/30/2023 12:00:00 AM","0.00","0.00","0.00","12770103"})
        );
        Collection<Tuple> persistenceList = List.of(
                newTuple(new String[] {"CORRECTO","399300","*0130","300000940749","128,430","","9/9/2024 12:00:00 AM" ,"9/9/2024 12:00:00 AM","0.00","0.00","0.00","13933601"}),
                newTuple(new String[] {"CORRECTO","399300","*7668","528209849528","60,000" ,"2024/08/21 19:28:32","8/22/2024 12:00:00 AM","8/22/2024 12:00:00 AM","0.00","0.00","0.00","21056387"})
        );

        when(input.getData()).thenReturn(inputList);
        when(persistence.getData()).thenReturn(persistenceList);

        // Act
        var result = validationService.processElements();

        // Assert
        assertEquals(inputList.size(), result.inputCount());
        assertEquals(persistenceList.size(), result.persistenceCount());
        assertEquals(3, result.inconsistenciesCount());

    }

    private ConsultaCLAITuple newTuple(String[] result) {
        return ConsultaCLAITuple.builder().rta(result[0])
                .numeroAutorizacion(result[1])
                .tarjeta(result[2])
                .cuenta(result[3])
                .valorOriginal(Money.from(result[4]))
                .fechaFinal(DateTime.from(result[5]))
                .fechaRegistroEncabezado(DateTime.from(result[6]))
                .fechaRegistroDetalle(DateTime.from(result[7]))
                .montoImpIVA(Money.from(result[8]))
                .montoBaseIVA(Money.from(result[9]))
                .propina(Money.from(result[10]))
                .comercio(result[11])
                .build();
    }
}