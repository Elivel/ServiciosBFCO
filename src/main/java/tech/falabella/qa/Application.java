package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.tuple.ConsultaCLAITuple;

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
                ? new SSRSReportAdapter<>(this.ssrsUrl, this.report.getRealName(), this.report.getTmpFile(), this.params, ConsultaCLAITuple.csvMap)
                : new FileReportAdapter<>(this.csvInput, this.separator, this.skipHeader, ConsultaCLAITuple.csvMap);

        final var persistence = new DDBBReportAdapter<>(DDBBReportAdapter.createConnection(this.mssqlUrl, this.username, this.password),
                this.report.getQuery(), this.report.validateAndGet(this.params),
                ConsultaCLAITuple.sqlMap);

        var reportValidation = ValidationServiceImpl.newInstance();
        reportValidation.setInputData(input);
        reportValidation.setPersistenceData(persistence);

        final var result = reportValidation.processElements();

        if (print) {
            result.forEach(e -> log.info(e.toString()));
        }

        return 0;
    }

}