package tech.falabella.qa.report.clearing_pmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class ClearingPMDConfig implements ReportConfig<ClearingPMDTuple> {

    private final Parameters parameters = Parameters.of(Map.of(
            "Fecha", Parameters.Value.of(1)
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
                    .valoresTrxCop(resultSet.getString(7))
                    .tiiTrxCop(resultSet.getString(8))
                    .netoTrxCop(resultSet.getString(9))
                    .cantidadReversosTrxCop(resultSet.getString(10))
                    .valoresReversosTrxCop(resultSet.getString(11))
                    .tiiReversosTrxCop(resultSet.getString(12))
                    .netoReversosTrxCop(resultSet.getString(13))
                    .cantidadTrx(resultSet.getString(14))
                    .valoresCompresionTrxCop(resultSet.getString(15))
                    .tiiComprensionTrxCop(resultSet.getString(16))
                    .netoCompensacionTrxCop(resultSet.getString(17))
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
                .valoresTrxCop(result[6])
                .tiiTrxCop(result[7])
                .netoTrxCop(result[8])
                .cantidadReversosTrxCop(result[9])
                .valoresReversosTrxCop(result[10])
                .tiiReversosTrxCop(result[11])
                .netoReversosTrxCop(result[12])
                .cantidadTrx(result[13])
                .valoresCompresionTrxCop(result[14])
                .tiiComprensionTrxCop(result[15])
                .netoCompensacionTrxCop(result[16])
                .build();
    }

}
