package tech.falabella.qa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tech.falabella.qa.tuple.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class FileReportAdapter<T extends Tuple> implements ReportPort {

    private Collection<T> data;

    private final String path;
    private final String separator;
    private final Function<String[], T> aMapFun;

    @Override
    public  void generate() {

        try {
            this.data = Files.readAllLines(Path.of(path))
                    .parallelStream()
                    .filter(it -> !it.isEmpty())
                    .map(it -> it.split(separator))
                    .filter(it -> it.length > 0)
                    .map(aMapFun)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
