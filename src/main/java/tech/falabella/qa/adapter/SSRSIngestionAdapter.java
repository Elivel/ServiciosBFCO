package tech.falabella.qa.adapter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Getter
@RequiredArgsConstructor
public class SSRSIngestionAdapter<T extends Tuple> implements IngestionPort {

    private final char separator;
    private final boolean skipHeader;
    private final String ssrsUrl;
    private final String reportName;
    private final String outPath;
    private final Map<String, String> parameters;
    private final Function<String[], T> aMapFun;
    private Collection<T> data;

    @Override
    public void generate() {
        String paramsAsQuery = parameters.entrySet().stream()
                .map(it -> it.getKey() + "=" + it.getValue())
                .collect(Collectors.joining("&"));
        var uri = ssrsUrl + "?/" + reportName + "&rs:Format=CSV&" + paramsAsQuery;

        // generate CSV
        try {
            var processBuilder = new ProcessBuilder();
            processBuilder.command("sh", "Invoke-WebRequest", "-Uri", uri, "-OutFile", outPath, "-UseDefaultCredentials", "-UseBasicParsing");
            var process = processBuilder.start();
            log.info("Generate CSV status: {}", process.waitFor());
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }

        // transform data
        var outFile = Path.of(outPath);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        try (Reader reader = Files.newBufferedReader(outFile);
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(skipHeader ? 1 : 0)
                     .withCSVParser(parser)
                     .build()) {
            this.data = csvReader.readAll().parallelStream().map(aMapFun).toList();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }

    }

}
