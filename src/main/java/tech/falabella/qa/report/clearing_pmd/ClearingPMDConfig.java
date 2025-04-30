package tech.falabella.qa.report.clearing_pmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class ClearingPMDConfig implements ReportConfig<ClearingPMDTuple> {

    private final Parameters parameters = Parameters.of(Map.of(
            "FechaProceso", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    }).build()
    ));

    private final String route = "Mastercard/Daily%20operation/CLEARING%20(PMD)";

    private final String query = """
            USE [CONCILIACION]
            
            DECLARE @return_value int
            EXEC @return_value = [nuevaconciliacion].[USP_MC_ObtenerDatosCuadreClearing]
            
            @Fecha = ?
            """;

    public ClearingPMDTuple sqlMap(ResultSet resultSet) {
        try {
            return ClearingPMDTuple.builder()
                    .producto(resultSet.getString(1))
                    .mti(resultSet.getString(2))
                    .functionCode(resultSet.getString(3))
                    .processingCode(resultSet.getString(4))
                    .conceptoClearingSettlement(resultSet.getString(5))
                    .cantidadTrxCop(resultSet.getString(6))
                    .valoresTrxCop(Money.from(resultSet.getString(7)))
                    .tiiTrxCop(Money.from(resultSet.getString(8)))
                    .netoTrxCop(Money.from(resultSet.getString(9)))
                    .cantidadReversosTrxCop(Money.from(resultSet.getString(10)))
                    .valoresReversosTrxCop(Money.from(resultSet.getString(11)))
                    .tiiReversosTrxCop(Money.from(resultSet.getString(12)))
                    .netoReversosTrxCop(Money.from(resultSet.getString(13)))
                    .cantidadTrx(Number.from(resultSet.getString(14)))
                    .valoresCompresionTrxCop(Money.from(resultSet.getString(15)))
                    .tiiComprensionTrxCop(Money.from(resultSet.getString(16)))
                    .netoCompensacionTrxCop(Money.from(resultSet.getString(17)))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public ClearingPMDTuple csvMap(String[] result) {
        return ClearingPMDTuple.builder()
                .producto(result[0])
                .mti(result[1])
                .functionCode(result[2])
                .processingCode(result[3])
                .conceptoClearingSettlement(result[4])
                .cantidadTrxCop(result[5])
                .valoresTrxCop(Money.from(result[6]))
                .tiiTrxCop(Money.from(result[7]))
                .netoTrxCop(Money.from(result[8]))
                .cantidadReversosTrxCop(Money.from(result[9]))
                .valoresReversosTrxCop(Money.from(result[10]))
                .tiiReversosTrxCop(Money.from(result[11]))
                .netoReversosTrxCop(Money.from(result[12]))
                .cantidadTrx(Number.from(result[13]))
                .valoresCompresionTrxCop(Money.from(result[14]))
                .tiiComprensionTrxCop(Money.from(result[15]))
                .netoCompensacionTrxCop(Money.from(result[16]))
                .build();
    }

}
