package tech.falabella.qa.report.ms_Detallado_QMR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class DetalladoQMRConfig implements ReportConfig<DetalladoQMRTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FECHAINI", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build(),
            "FECHAFIN", Parameters.Value.builder()
                    .position(2)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build()
    ));
    private final String route = "Mastercard/Reports/326_REPORTE_DETALLE_QMR";

    private final String query = """
USE [CONCILIACION]

DECLARE @return_value int
EXEC	@return_value = [nuevaconciliacion].[USP_SELECT_REPORTE_326_QMR]
		@FECHAINI =?,
		@FECHAFIN = ?
""";
    private final int headerRowSize = 4;
    @Override
    public DetalladoQMRTuple sqlMap(ResultSet resultSet) {
        try{
            return DetalladoQMRTuple.builder()
                    .codigomcc(Number.from(resultSet.getString(1)))
                    .servidora(resultSet.getString(2))
                    .comercio(Number.from(resultSet.getString(3)))
                    .nombrecomercio(resultSet.getString(4))
                    .funcion(resultSet.getString(5))
                    .nombrefuncion(resultSet.getString(6))
                    .fecha(DateTime.from(resultSet.getString(7)))
                    .bin(resultSet.getString(8))
                    .fuente(resultSet.getString(9))
                    .mumtrx(resultSet.getString(10))
                    .valortrx(Number.from(resultSet.getString(11)))
                    .impuestoconsumo(Money.from(resultSet.getString(12)))
                    .baseiva(Money.from(resultSet.getString(13)))
                    .iva(Money.from(resultSet.getString(14)))
                    .propina(Money.from(resultSet.getString(15)))
                    .tipotransaccion(resultSet.getString(16))
                    .tipobin(resultSet.getString(17))
                    .build();

        }catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    @Override
    public DetalladoQMRTuple csvMap(String[] result) {
        return DetalladoQMRTuple.builder()
                .codigomcc(Number.from(result[0]))
                .servidora(result[1])
                .comercio(Number.from(result[2]))
                .nombrecomercio(result[3])
                .funcion(result[4])
                .nombrefuncion(result[5])
                .fecha(DateTime.from(result[6]))
                .bin(result[7])
                .fuente(result[8])
                .mumtrx(result[9])
                .valortrx(Number.from(result[10]))
                .impuestoconsumo(Money.from(result[11]))
                .baseiva(Money.from(result[12]))
                .iva(Money.from(result[13]))
                .propina(Money.from(result[14]))
                .tipotransaccion(result[15])
                .tipobin(result[16])
                .build();


    }
}
