package tech.falabella.qa.report.detallado_evento_mcca;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.ReportConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class DetalladoEventoMCCAConfig implements ReportConfig<DetalladoEventoMCCATuple> {

    private final Map<String, Integer> parameters = Map.of("Fecha", 1, "Evento", 2);

    private final String route = "Mastercard/Billing/DETALLADO%20POR%20EVENTO%20MCCA";

    private final String query = """
            USE [CONCILIACION]
            
            DECLARE @return_value int
            EXEC @return_value = [nuevaconciliacion].[USP_SEL_REP_ITEM_EVENTO_D]
            
            @FechaProceso = ?,
            @ID = ?
            """;

    public DetalladoEventoMCCATuple sqlMap(ResultSet resultSet) {
        try {
            return DetalladoEventoMCCATuple.builder()
                    .tipoMensaje(resultSet.getString(1))
                    .codigoFuncion(resultSet.getString(2))
                    .tarjeta(resultSet.getString(3))
                    .tipoTransaccion(resultSet.getString(4))
                    .fecha(resultSet.getString(5))
                    .codigoComercio(resultSet.getString(6))
                    .nombreComercio(resultSet.getString(7))
                    .estadoComercio(resultSet.getString(8))
                    .paisComercio(resultSet.getString(9))
                    .valorTransaccion(resultSet.getString(10))
                    .valorIva(resultSet.getString(11))
                    .numAprobacion(resultSet.getString(12))
                    .traceId(resultSet.getString(13))
                    .refArn(resultSet.getString(14))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public DetalladoEventoMCCATuple csvMap(String[] result) {
        return DetalladoEventoMCCATuple.builder()
                .tipoMensaje(result[0])
                .codigoFuncion(result[1])
                .tarjeta(result[2])
                .tipoTransaccion(result[3])
                .fecha(result[4])
                .codigoComercio(result[5])
                .nombreComercio(result[6])
                .estadoComercio(result[7])
                .paisComercio(result[8])
                .valorTransaccion(result[9])
                .valorIva(result[10])
                .numAprobacion(result[11])
                .traceId(result[12])
                .refArn(result[13])
                .build();
    }
}
