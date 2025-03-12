package tech.falabella.qa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tech.falabella.qa.tuple.Tuple;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class FileReportAdapter<T extends Tuple> implements ReportPort {

    private Collection<T> data;

    private final File path;
    private final String separator;
    private final Function<String[], T> aMapFun;

    @Override
    public void generate() {

        try {
            this.data = Files.readAllLines(path.toPath())
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
