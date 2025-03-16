package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.tuple.ConsultaCLAITuple;
import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;
import java.util.concurrent.Callable;

@Slf4j
@NoArgsConstructor
public class Application extends CommandArgs implements Callable<Integer> {

    public static void main(String[] args) {
        var commandLine = new CommandLine(new Application());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        //var defaultsFile = new File("/Volumes/Datos/workspace/freelance/falabella/sql-server-reporting-automation/src/main/resources/.ssrs-validator.properties");
        //commandLine.setDefaultValueProvider(new CommandLine.PropertiesDefaultProvider(defaultsFile));

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        final var input = (executeExportReport)
                ? new SSRSReportAdapter<>(this.separator.value, this.skipHeader, this.ssrsUrl, this.report.getRealName(), this.report.getTmpFile(this.outPath), this.params, ConsultaCLAITuple.csvMap)
                : new FileReportAdapter<>(this.csvInput, this.separator.value, this.skipHeader, ConsultaCLAITuple.csvMap);

        final var persistence = new DDBBReportAdapter<>(DDBBReportAdapter.createConnection(this.mssqlUrl, this.username, this.password),
                this.report.getQuery(), this.report.validateAndGet(this.params),
                ConsultaCLAITuple.sqlMap);

        var reportValidation = ValidationServiceImpl.newInstance();
        reportValidation.setInputData(input);
        reportValidation.setPersistenceData(persistence);

        final var result = reportValidation.processElements();

        if (print) {
            printResultInConsole(result);
        }

        return 0;
    }

    private <T extends Tuple> void printResultInConsole(Collection<T> result) {
        var header = """
                Área:                   BFCO Cluster Proyectos Corporativos - Quality Assurance
                Nombre del Analista:    Johan Stid Melgarejo Gómez
                Caso de Prueba:         BFCOGP-4144 - [CP006]-[BFCOGP-3696] - Carpeta: Conciliacion Liquidacion - Validación del reporte CONSULTA_CLAI
                Certificación:          HU: [BFCOGP-3696] - Migración reportes de Conciliación
                Datos De Prueba:        NroAutorizacion: 399300
                Fecha de Ejecución:     07/11/2024
                Ciclo de Pruebas:       01
                Plataforma:
                    SQL Server Reporting Services: http://f8sc00008/Reports/browse/
                    Local file:         /Desktop/consultaCLAI2.csv
                Precondición:           Tablas pobladas
                Estado de la Prueba:    Exitoso
        """;
        log.info("\n"+header);
        result.forEach(e -> log.info(e.toString()));
    }

}