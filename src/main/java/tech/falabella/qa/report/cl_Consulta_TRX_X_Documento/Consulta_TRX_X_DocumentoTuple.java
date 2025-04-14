package tech.falabella.qa.report.cl_Consulta_TRX_X_Documento;
import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class Consulta_TRX_X_DocumentoTuple extends Tuple{
    private String canal;
    private String tipoTransaccion;
    private String tarjeta;
    private String numeroCuenta;
    private String codigoAutorizacion;
    private String fecha;
    private String hora;
    private String valorTransaccion;
    private String codigoConvenio;
    private String nombreComercio;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Nombre Comercio", nombreComercio,
                        "Canal", canal,
                        "Tipo Transación", tipoTransaccion)


        );
    }
}
