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

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Getter
@RequiredArgsConstructor
public class FileIngestionAdapter<T extends Tuple> implements IngestionPort {

    private final File path;
    private final char separator;
    private final boolean skipHeader;
    private final int headerRowSize;
    private final Function<String[], T> aMapFun;
    private Collection<T> data;

    @Override
    public void generate() {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(false)
                .withQuoteChar('"')
                .build();

        try (Reader reader = Files.newBufferedReader(path.toPath());
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(skipHeader ? headerRowSize : 0)
                     .withCSVParser(parser)
                     .build()) {
            this.data = csvReader.readAll().parallelStream()
                    .filter(line -> line.length > 0)
                    .filter(line -> Stream.of(line).anyMatch(cell -> !cell.trim().isEmpty()))
                    .map(aMapFun)
                    .filter(Objects::nonNull).toList();

        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }

    }

}
