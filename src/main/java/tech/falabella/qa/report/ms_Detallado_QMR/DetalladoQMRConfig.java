package tech.falabella.qa.report.ms_Detallado_QMR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class DetalladoQMRConfig implements ReportConfig<DetalladoQMRTupla> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FECHA_INICIAL", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build(),
            "FECHA_FINAL", Parameters.Value.builder()
                    .position(2)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build()
    ));
    private final String route = "Mastercard/Billing/326_REPORTE_DETALLE_QMR";

    private final String query = """
USE [CONCILIACION]

DECLARE @return_value int
EXEC	@return_value = [nuevaconciliacion].[USP_SELECT_REPORTE_326_QMR]
		@FECHAINI =?,
		@FECHAFIN = ?'
""";

    @Override
    public DetalladoQMRTupla sqlMap(ResultSet resultSet) {
        return null;
    }

    @Override
    public DetalladoQMRTupla csvMap(String[] result) {
        return null;
    }
}
