package tech.falabella.qa;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.tuple.Tuple;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Collection;
import java.util.function.Function;

@Slf4j
@Getter
@RequiredArgsConstructor
public class FileReportAdapter<T extends Tuple> implements ReportPort {

    private Collection<T> data;

    private final File path;
    private final char separator;
    private final boolean skipHeader;
    private final Function<String[], T> aMapFun;

    @Override
    public void generate() {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        try (Reader reader = Files.newBufferedReader(path.toPath());
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
