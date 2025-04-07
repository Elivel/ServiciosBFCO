package tech.falabella.qa.report.detallado_evento_mcca;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class DetalladoEventoMCCAConfig implements ReportConfig<DetalladoEventoMCCATuple> {

    private final Parameters parameters = Parameters.of(Map.of(
            "Fecha", Parameters.Value.of(1),
            "Evento", Parameters.Value.builder().position(2).type("select").action(dVal -> Optional.ofNullable(Evento.fromValue(dVal)).map(it -> it.value).orElse("")).build()
    ));

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

    @AllArgsConstructor
    public enum Evento {
        ISSUER_DOMESTIC_CLEARING_FEE_MICRO("1", "Issuer Domestic Clearing Fee Micro"),
        ISSUER_DOMESTIC_CLEARING_FEE_SMALL("2", "Issuer Domestic Clearing Fee Small"),
        ISSUER_DOMESTIC_CLEARING_FEE_MID("3", "Issuer Domestic Clearing Fee Mid"),
        ISSUER_DOMESTIC_CLEARING_FEE_LARGE("4", "Issuer Domestic Clearing Fee Large"),
        ISSUER_DOMESTIC_CLEARING_FEE_MAX("5", "Issuer Domestic Clearing Fee Max"),
        AUTHORIZATION_ISSUER_FEE_MICRO_TIER("6", "Authorization Issuer Fee Micro Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_SMALL_TIER("7", "Authorization Issuer Fee Domestic Small Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MID_TIER("8", "Authorization Issuer Fee Domestic Mid Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_LARGE_TIER("9", "Authorization Issuer Fee Domestic Large Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MAX_TIER("10", "Authorization Issuer Fee Domestic Max Tier"),
        AUTHORIZATION_ISSUER_FEE_MICRO_ACQ_GENERATED_REVERSAL("11", "Authorization Issuer Fee—Micro Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_SMALL_ACQ_GENERATED_REVERSAL("12", "Authorization Issuer Fee—Small Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_MID_ACQ_GENERATED_REVERSAL("13", "Authorization Issuer Fee—Mid Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_LARGE_ACQ_GENERATED_REVERSAL("14", "Authorization Issuer Fee Domestic—Large Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MAX_ACQ_GENERATED_REVERSAL("15", "Authorization Issuer Fee Domestic—Max Acq Generated Reversal");

        public final String value;
        public final String description;

        public static Evento fromValue(String value) {
            for (Evento it : Evento.values()) {
                if (it.name().equalsIgnoreCase(value) || it.value.equalsIgnoreCase(value) || it.description.equalsIgnoreCase(value))
                    return it;
            }
            return null; //throw new EnumConstantNotPresentException(Evento.class, value);
        }

    }
}
