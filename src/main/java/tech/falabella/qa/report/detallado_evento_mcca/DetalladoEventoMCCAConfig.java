package tech.falabella.qa.report.detallado_evento_mcca;

import lombok.AllArgsConstructor;
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
import java.util.Optional;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class DetalladoEventoMCCAConfig implements ReportConfig<DetalladoEventoMCCATuple> {

    private final Parameters parameters = Parameters.of(Map.of(
            "FECHA", Parameters.Value.builder()
                                    .position(1)
                                    .sqlFormat(dVal -> {
                                        if (null == dVal || dVal.isBlank())
                                            return "";
                                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                                        return date.format(formatterSql);
                                    })
                    .build(),
            "EVENTO", Parameters.Value.builder().position(2).type("select")
                    .action(dVal -> Optional.ofNullable(Evento.fromValue(dVal)).map(it -> it.description).orElse(""))
                    .sqlFormat(dVal -> Optional.ofNullable(Evento.fromValue(dVal)).map(it -> it.internalId).orElse(""))
                    .build()
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
                    .rep_tipoMensaje(resultSet.getString(5))
                    .rep_codigoFuncion(Number.from(resultSet.getString( 6)))
                    .rep_tarjeta(resultSet.getString(7))
                    .rep_tipoTransaccion(resultSet.getString(8))
                    .rep_fecha(DateTime.from(resultSet.getString(9)))
                    .rep_codigoComercio(resultSet.getString(10))
                    .rep_nombreComercio(resultSet.getString(11))
                    .rep_estadoComercio(resultSet.getString(12))
                    .rep_paisComercio(resultSet.getString(13))
                    .rep_valorTransaccion(Money.from(resultSet.getString(14)))
                    .rep_valorIva(Money.from(resultSet.getString(15)))
                    .rep_numAprobacion(resultSet.getString(16))
                    .rep_traceId(resultSet.getString(17))
                    .rep_refArn(resultSet.getString(18))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public DetalladoEventoMCCATuple csvMap(String[] result) {
        return DetalladoEventoMCCATuple.builder()
                .rep_tipoMensaje(result[0])
                .rep_codigoFuncion(Number.from(result[1]))
                .rep_tarjeta(result[2])
                .rep_tipoTransaccion(result[3])
                .rep_fecha(DateTime.from(result[4]))
                .rep_codigoComercio(result[5])
                .rep_nombreComercio(result[6])
                .rep_estadoComercio(result[7])
                .rep_paisComercio(result[8])
                .rep_valorTransaccion(Money.from(result[9]))
                .rep_valorIva(Money.from(result[10]))
                .rep_numAprobacion(result[11])
                .rep_traceId(result[12])
                .rep_refArn(result[13])
                .build();
    }

    @AllArgsConstructor
    public enum Evento {
        ISSUER_DOMESTIC_CLEARING_FEE_MICRO("1", "1", "Issuer Domestic Clearing Fee Micro"),
        ISSUER_DOMESTIC_CLEARING_FEE_SMALL("2", "2", "Issuer Domestic Clearing Fee Small"),
        ISSUER_DOMESTIC_CLEARING_FEE_MID("3", "3", "Issuer Domestic Clearing Fee Mid"),
        ISSUER_DOMESTIC_CLEARING_FEE_LARGE("4", "4", "Issuer Domestic Clearing Fee Large"),
        ISSUER_DOMESTIC_CLEARING_FEE_MAX("5", "5", "Issuer Domestic Clearing Fee Max"),
        AUTHORIZATION_ISSUER_FEE_MICRO_TIER("6", "21", "Authorization Issuer Fee Micro Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_SMALL_TIER("7", "22", "Authorization Issuer Fee Domestic Small Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MID_TIER("8", "23", "Authorization Issuer Fee Domestic Mid Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_LARGE_TIER("9", "24", "Authorization Issuer Fee Domestic Large Tier"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MAX_TIER("10", "25", "Authorization Issuer Fee Domestic Max Tier"),
        AUTHORIZATION_ISSUER_FEE_MICRO_ACQ_GENERATED_REVERSAL("11", "26", "Authorization Issuer Fee—Micro Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_SMALL_ACQ_GENERATED_REVERSAL("12", "27", "Authorization Issuer Fee—Small Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_MID_ACQ_GENERATED_REVERSAL("13", "28", "Authorization Issuer Fee—Mid Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_LARGE_ACQ_GENERATED_REVERSAL("14", "29", "Authorization Issuer Fee Domestic—Large Acq Generated Reversal"),
        AUTHORIZATION_ISSUER_FEE_DOMESTIC_MAX_ACQ_GENERATED_REVERSAL("15", "30", "Authorization Issuer Fee Domestic—Max Acq Generated Reversal");

        public final String value;
        public final String internalId;
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
