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
                    .ciclo(resultSet.getString(1))
                    .producto(resultSet.getString(2))
                    .definicionOrden(resultSet.getString(3))
                    .mti(resultSet.getString(4))
                    .functionCode(resultSet.getString(5))
                    .functionDescription(resultSet.getString(6))
                    .processingCode(resultSet.getString(7))
                    .conceptoClearingSettlement(resultSet.getString(8))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public ClearingPMDTuple csvMap(String[] result) {
        return ClearingPMDTuple.builder()
                .ciclo(result[0])
                .producto(result[1])
                .definicionOrden(result[2])
                .mti(result[3])
                .functionCode(result[4])
                .functionDescription(result[5])
                .processingCode(result[6])
                .conceptoClearingSettlement(result[7])
                .build();
    }

}
