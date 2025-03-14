package tech.falabella.qa;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.tuple.Tuple;

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
public class SSRSReportAdapter<T extends Tuple> implements ReportPort {

    private Collection<T> data;

    private final char separator = ',';
    private final boolean skipHeader = true;

    private final String ssrsUrl;
    private final String reportName;
    private final String outPath;
    private final Map<String, String> parameters;
    private final Function<String[], T> aMapFun;

    @Override
    public void generate() {
        //"http://localhost/ReportServer?/score_report&StudentID=$($student_id)&rs:Format=PDF";
        String paramsAsQuery = parameters.entrySet().stream()
                .map(it -> it.getKey() + "=" + it.getValue())
                .collect(Collectors.joining("&"));
        var uri = ssrsUrl + "?/" + reportName + "&rs:Format=CSV&" + paramsAsQuery;

        // generate CSV
        try {
            Runtime.getRuntime().exec(new String[]{"Invoke-WebRequest", "-Uri", uri, "-UseDefaultCredentials", "-UseBasicParsing"});
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
