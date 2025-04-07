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

    private String tipoMensaje;
    private String codigoFuncion;
    private String tarjeta;
    private String tipoTransaccion;
    private String fecha;
    private String codigoComercio;
    private String nombreComercio;
    private String estadoComercio;
    private String paisComercio;
    private String valorTransaccion;
    private String valorIva;
    private String numAprobacion;
    private String traceId;
    private String refArn;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("tarjeta", tarjeta,
                        "trace-id", traceId)
        );
    }

}
