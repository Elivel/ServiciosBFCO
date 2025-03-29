package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.adapter.DDBBReportAdapter;
import tech.falabella.qa.adapter.FileReportAdapter;
import tech.falabella.qa.adapter.SSRSReportAdapter;
import tech.falabella.qa.exception.DisabledReportException;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.service.ValidationServiceImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class Application extends CommandArgs implements Callable<Integer> {

    public static void main(String[] args) {

        var commandLine = new CommandLine(new Application());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        if (!this.report.enabled)
            throw new DisabledReportException(this.report.name());

        var reportConfig = this.report.config.get();

        final var input = (executeExportReport)
                ? new SSRSReportAdapter<>(this.separator.value, this.skipHeader, this.ssrsUrl, reportConfig.getRoute(), this.outPath, this.params, reportConfig::csvMap)
                : new FileReportAdapter<>(this.csvInput, this.separator.value, this.skipHeader, reportConfig::csvMap);

        final var persistence = new DDBBReportAdapter<>(DDBBReportAdapter.createConnection(this.mssqlUrl, this.username, this.password),
                reportConfig.getQuery(), reportConfig.validateAndGet(this.params),
                reportConfig::sqlMap);

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
                        Nombre del Analista:    %s
                        Caso de Prueba:         %s
                        Certificación:          Migración reportes de Conciliación
                        Datos De Prueba:        %s
                        Fecha de Ejecución:     %td/%<tm/%<tY
                        Ciclo de Pruebas:       01
                        Plataforma:
                            %s
                        Precondición:           Tablas pobladas
                        Estado de la Prueba:    %s
                """.formatted(
                System.getProperty("user.name"),
                this.report.name(),
                this.params.entrySet().stream()
                        .filter(it -> !it.getValue().isBlank())
                        .map(it -> it.getKey().concat(": ").concat(it.getValue()))
                        .collect(Collectors.joining(", ")),
                LocalDate.now(),
                this.executeExportReport
                        ? "SQL Server Reporting Services: ".concat(this.ssrsUrl)
                        : "Local file: ".concat(this.csvInput.getPath()),
                result.isEmpty() ? "Exitoso" : "Fallido"
        );

        log.info("\n".concat(header));
        result.forEach(e -> log.info(e.toString()));
    }

}