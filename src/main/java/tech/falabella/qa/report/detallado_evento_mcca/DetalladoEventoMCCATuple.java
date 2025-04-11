package tech.falabella.qa.report.detallado_evento_mcca;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class DetalladoEventoMCCATuple extends Tuple {

    private String rep_tipoMensaje;
    private String rep_codigoFuncion;
    private String rep_tarjeta;
    private String rep_tipoTransaccion;
    private String rep_fecha;
    private String rep_codigoComercio;
    private String rep_nombreComercio;
    private String rep_estadoComercio;
    private String rep_paisComercio;
    private String rep_valorTransaccion;
    private String rep_valorIva;
    private String rep_numAprobacion;
    private String rep_traceId;
    private String rep_refArn;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("tarjeta", rep_tarjeta,
                        "trace-id", rep_traceId,
                        "codigo de comercio", rep_codigoComercio)
        );
    }

}
