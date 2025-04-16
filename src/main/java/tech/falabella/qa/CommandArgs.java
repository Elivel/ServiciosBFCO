package tech.falabella.qa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import tech.falabella.qa.adapter.DDBBIngestionAdapter;
import tech.falabella.qa.adapter.FileIngestionAdapter;
import tech.falabella.qa.adapter.ScrapingIngestionAdapter;
import tech.falabella.qa.dto.Report;
import tech.falabella.qa.dto.Separator;
import tech.falabella.qa.exception.DisabledReportException;
import tech.falabella.qa.port.IngestionPort;

import java.io.File;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.Callable;

@Getter
@NoArgsConstructor(staticName = "newInstance")
@Command(name = "ssrs-validator", mixinStandardHelpOptions = true, version = {
        "ssrs-validator 0.1.0-SNAPSHOT",
        "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
        "OS: ${os.name} ${os.version} ${os.arch}"},
        description = "valida la generación de reportes en SSRS")
public class CommandArgs implements Callable<Integer> {

    @Option(names = {"-n", "--report-name"}, required = true, description = "Report name")
    Report report;

    @Option(names = "-D", mapFallbackValue = Option.NULL_VALUE, hideParamSyntax = true, description = "Dynamic parameters for report generation")
    Map<String, String> params;

    // file
    @Option(names = {"-f", "--file"}, description = "The archive file")
    File csvInput;

    @Option(names = {"-s", "--separator"}, description = "The char separator data in CSV file. Default (',')", defaultValue = "comma")
    Separator separator;

    @Option(names = {"--skip-header"}, description = "Skip first line in CSV file. Default (FALSE)", negatable = true, defaultValue = "false", fallbackValue = "true")
    boolean skipHeader;

    // Database connection
    @Option(names = {"-m", "--mssql-url"}, required = true, description = "URL to access database")
    String mssqlUrl;

    @Option(names = {"-U", "--username"}, required = true, description = "User name for database", fallbackValue = "sa")
    String username;

    @Option(names = {"-P", "--password"}, required = true, description = "Passphrase for database")
    String password;

    // Reporting service
    @Option(names = {"-r", "--ssrs-url"}, description = "URL to access reporting service")
    String ssrsUrl;

    @Option(names = {"-e", "--execute-export-report"}, negatable = true, defaultValue = "false", fallbackValue = "true", description = "Indicates that the report generation process should be run from the reporting service")
    boolean executeExportReport;

    @Option(names = {"-x", "--out-path-export"}, description = "path export file")
    String outPath;

    // others
    @Option(names = {"-p", "--print"}, description = "display output", negatable = true, defaultValue = "false", fallbackValue = "true")
    boolean print;

    @Option(names = {"-o", "--out-file-result"}, description = "file output result")
    String output;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean helpRequested = false;

    @Option(names = {"-v", "--version"}, versionHelp = true,
            description = "print version information")
    boolean versionRequested;

    public IngestionPort generateInput() {
        var reportConfig = this.report.config.get();

        return (executeExportReport)
                ? new ScrapingIngestionAdapter<>(this.ssrsUrl.concat(reportConfig.getRoute()), reportConfig.assignDefaultValuesAndGet(this.params), this.outPath, this.separator.value, this.skipHeader, reportConfig.getHeaderRowSize(), reportConfig::csvMap)
                : new FileIngestionAdapter<>(this.csvInput, this.separator.value, this.skipHeader, reportConfig.getHeaderRowSize(), reportConfig::csvMap);
    }

    public IngestionPort generatePersistence() {
        return generatePersistence(DDBBIngestionAdapter.createConnection(this.mssqlUrl, this.username, this.password));
    }

    public IngestionPort generatePersistence(Connection connection) {
        var reportConfig = this.report.config.get();

        return new DDBBIngestionAdapter<>(connection,
                reportConfig.getQuery(),
                reportConfig.validateAndGet(this.params),
                reportConfig::sqlMap);
    }

    @Override
    public Integer call() throws Exception {
        if (!this.report.enabled)
            throw new DisabledReportException(this.report.name());

        return 0;
    }

}
