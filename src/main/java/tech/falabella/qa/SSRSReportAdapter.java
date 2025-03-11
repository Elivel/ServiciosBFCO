package tech.falabella.qa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tech.falabella.qa.tuple.Tuple;

import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class SSRSReportAdapter implements ReportPort {

    private Collection<? extends Tuple> data;

    private final String reportName;
    private final String outPath;
    private final Map<String[], ? extends Tuple> parameters;

    @Override
    public void generate() {
        throw new InternalError("No implemented");
    }

}
