package tech.falabella.qa.adapter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import lombok.SneakyThrows;
import org.h2.tools.Csv;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.report.cl_ConsolidadoLiq.ConsolidadoLiqConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class DDBBIngestionAdapterTest {

    CSVParser parser = new CSVParserBuilder()
            .withSeparator(',')
            .withIgnoreQuotations(false)
            .withQuoteChar('"')
            .build();

    @SneakyThrows
    private ResultSet getMockResultSetFrom(String path) {
        var data = Files.readString(Path.of(path));
        var header = data.split("\n")[0].split(",");
        data = data.substring(data.indexOf("\n")+1);

        return new Csv().read(new StringReader(data), header);
    }

    @SneakyThrows
    private <T extends Tuple> IngestionPort getAdapter(String path, String query, Function<ResultSet, T> aMapFun) {
        var prepareStatement = Mockito.mock(PreparedStatement.class);
        doNothing().when(prepareStatement).setString(anyInt(), anyString());
        when(prepareStatement.executeQuery()).thenReturn(getMockResultSetFrom(path));
        var connection = Mockito.mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenReturn(prepareStatement);

        return new DDBBIngestionAdapter<>(connection, query, Map.of(), aMapFun);
    }

    //@Test
    void generateFromConsultaClai() {
        var path = "src/test/resources/data/query/CONSULTA_CLAI.csv";
        var config = ConsultaCLAIConfig.newInstance();
        var adapter = getAdapter(path, config.getQuery(), config::sqlMap);

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
    }

    @Test
    void generateFromDetalladoEventoMCCA() {
        var path = "src/test/resources/data/query/DETALLADO_EVENTO_MCCA.csv";
        var config = DetalladoEventoMCCAConfig.newInstance();
        var adapter = getAdapter(path, config.getQuery(), config::sqlMap);

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(10427, result.size());
    }

    @Test
    void generateFromConsolidadoLiq() {
        var path = "src/test/resources/data/query/CONSOLIDADO_LIQ.csv";
        var config = ConsolidadoLiqConfig.newInstance();
        var adapter = getAdapter(path, config.getQuery(), config::sqlMap);

        adapter.generate();
        var result = adapter.getData();

        assertFalse(result.isEmpty());
        assertEquals(449, result.size());
    }

}