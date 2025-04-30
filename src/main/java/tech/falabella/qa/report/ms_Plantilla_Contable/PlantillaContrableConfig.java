package tech.falabella.qa.report.ms_Plantilla_Contable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class PlantillaContrableConfig implements ReportConfig <PlantillaContrableTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FechaProceso", Parameters.Value.builder()
                    .position(1)
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
    private final String route = "Mastercard/Daily operation/PLANILLA CONTABLE (PMD)";
    private final String query = """
            USE [CONCILIACION]
            DECLARE	@return_value int
            EXEC	@return_value = [nuevaconciliacion].[USP_MC_ObtenerDatosCuentasCompensacion]
          	@FechaProceso = ?,
            """;

    @Override
    public PlantillaContrableTuple sqlMap(ResultSet resultSet) {
        return null;
    }

    @Override
    public PlantillaContrableTuple csvMap(String[] result) {
        return null;
    }
}
