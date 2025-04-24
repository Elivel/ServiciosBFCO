package tech.falabella.qa.report.detallado_evento_mcca;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class DetalladoEventoMCCATuple extends Tuple {

    private String rep_tipoMensaje;
    private Number rep_codigoFuncion;
    private String rep_tarjeta;
    private String rep_tipoTransaccion;
    private DateTime rep_fecha;
    private String rep_codigoComercio;
    private String rep_nombreComercio;
    private String rep_estadoComercio;
    private String rep_paisComercio;
    private Money rep_valorTransaccion;
    private Money rep_valorIva;
    private String rep_numAprobacion;
    private String rep_traceId;
    private String rep_refArn;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("tarjeta", rep_tarjeta,
                        "trace-id", rep_traceId,
                        "codigo-comercio", rep_codigoComercio)
        );
    }

}
