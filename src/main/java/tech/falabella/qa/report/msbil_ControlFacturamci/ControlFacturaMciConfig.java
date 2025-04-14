package tech.falabella.qa.report.msbil_ControlFacturamci;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class ControlFacturaMciConfig implements ReportConfig<ControlFacturaMciTuple>{
    private final  Parameters parameters = Parameters.of(Map.of(

    ));
    private final String route ="Mastercard/Billing/CONTROL FACTURA MCI";
    private final String query = """
            USE [CONCILIACION]
           
            DECLARE	@return_value int
            EXEC	@return_value = [nuevaconciliacion].[USP_CONTROL_FACTURA]
          
            """;
    public ControlFacturaMciTuple sqlMap(ResultSet resultSet) {
        try {
            return ControlFacturaMciTuple.builder()
                    .fecha(resultSet.getString(1))
                    .eventCode(resultSet.getString(2))
                    .groupServiceCode(resultSet.getString(3))
                    .name(resultSet.getString(4))
                    .valueA(resultSet.getString(5))
                    .rate(resultSet.getString(6))
                    .charge(resultSet.getString(7))
                    .valorInvoice(resultSet.getString(8))
                    .rateValor(resultSet.getString(9))
                    .chargeInvoide(resultSet.getString(10))
                    .efectividadControlFacturaMci(resultSet.getString(11))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ControlFacturaMciTuple csvMap(String[] result) {
        return ControlFacturaMciTuple.builder()
                .fecha(result[0])
                .eventCode(result[1])
                .groupServiceCode(result[2])
                .name(result[3])
                .valueA(result[4])
                .rate(result[5])
                .charge(result[6])
                .valorInvoice(result[7])
                .rateValor(result[8])
                .chargeInvoide(result[9])
                .efectividadControlFacturaMci(result[10])
                .build();


    }
}
