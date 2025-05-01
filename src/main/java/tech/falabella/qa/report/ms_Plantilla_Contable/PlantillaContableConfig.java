package tech.falabella.qa.report.ms_Plantilla_Contable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.type.Money;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class PlantillaContableConfig implements ReportConfig <PlantillaContableTuple> {
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
          	@FechaProceso = ?
            """;

    @Override
    public PlantillaContableTuple sqlMap(ResultSet resultSet) {
        try {
            return PlantillaContableTuple.builder()
                    .codigocuenta(resultSet.getString(6))
                    .nombretercero(resultSet.getString(7))
                    .nittercero(resultSet.getString(8))
                    .centrocosto(resultSet.getString(9))
                    .debito(Money.from(resultSet.getString(10)))
                    .credito(Money.from(resultSet.getString(11)))
                    .build();
        }catch (SQLException e){
            throw new MalformedTupleException(e);

        }
    }

    @Override
    public PlantillaContableTuple csvMap(String[] result) {

        return PlantillaContableTuple.builder()
                .codigocuenta(result[2])
                .nombretercero(result[3])
                .nittercero(result[4])
                .centrocosto(result[5])
                .debito(Money.from(result[6]))
                .credito(Money.from(result[7]))
                .build();
    }
}
