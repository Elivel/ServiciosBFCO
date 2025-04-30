package tech.falabella.qa.report.msbil_ControlFacturamci;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
                    .fecha(DateTime.from(resultSet.getString(1)))
                    .eventCode(resultSet.getString(2))
                    .groupServiceCode(resultSet.getString(3))
                    .name(resultSet.getString(4))
                    .valueA(Money.from (resultSet.getString(5)))
                    .rate(Money.from(resultSet.getString(6)))
                    .charge(Money.from(resultSet.getString(7)))
                    .valorInvoice(Money.from(resultSet.getString(8)))
                    .rateValor(Money.from (resultSet.getString(9)))
                    .chargeInvoide(Money.from(resultSet.getString(10)))
                    .efectividadControlFacturaMci(resultSet.getString(11))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ControlFacturaMciTuple csvMap(String[] result) {
        return ControlFacturaMciTuple.builder()
                .fecha(DateTime.from(result[0]))
                .eventCode(result[1])
                .groupServiceCode(result[2])
                .name(result[3])
                .valueA(Money.from(result[4]))
                .rate(Money.from(result[5]))
                .charge(Money.from(result[6]))
                .valorInvoice(Money.from(result[7]))
                .rateValor(Money.from(result[8]))
                .chargeInvoide(Money.from(result[9]))
                .efectividadControlFacturaMci(result[10])
                .build();


    }
}
