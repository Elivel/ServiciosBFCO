package tech.falabella.qa.adapter;

import org.junit.jupiter.api.Test;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.report.cl_ConsolidadoLiq.ConsolidadoLiqConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FileIngestionAdapterTest {

    private <T extends Tuple> IngestionPort getAdapter(String path, ReportConfig<T> config) {
        var file = new File(path);
        return new FileIngestionAdapter<>(file, ',', Boolean.TRUE, config.getHeaderRowSize(), config::csvMap);
    }

    @Test
    void generateFromConsultaClai() {
        var path = "src/test/resources/data/export/CONSULTA_CLAI.csv";
        var adapter = getAdapter(path, ConsultaCLAIConfig.newInstance());

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void generateFromDetalladoEventoMCCA() {
        var path = "src/test/resources/data/export/DETALLADO_EVENTO_MCCA.csv";
        var adapter = getAdapter(path, DetalladoEventoMCCAConfig.newInstance());

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(10427, result.size());
    }

    @Test
    void generateFromConsolidadoLiq() {
        var path = "src/test/resources/data/export/CONSOLIDADO_LIQ.csv";
        var adapter = getAdapter(path, ConsolidadoLiqConfig.newInstance());

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(449, result.size());
    }

}