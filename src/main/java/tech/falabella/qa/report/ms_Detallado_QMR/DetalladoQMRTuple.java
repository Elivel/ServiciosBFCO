package tech.falabella.qa.report.ms_Detallado_QMR;

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
public class DetalladoQMRTuple extends Tuple {
    private String codigomcc;
    private String servidora;
    private String comercio;
    private String nombrecomercio;
    private String funcion;
    private String nombrefuncion;
    private DateTime fecha;
    private String bin;
    private String fuente;
    private String mumtrx;
    private Number valortrx;
    private Money impuestoconsumo;
    private Money baseiva;
    private Money iva;
    private Money propina;
    private String tipotransaccion;
    private String tipobin;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("comercio",comercio,
                        "funcion",funcion,
                        "bin",bin,
                        "fecha",(null != fecha && null != fecha.value()) ? fecha.value().toString() : "",
                        "codigo-mcc",codigomcc,
                        "valor-trx", (null != valortrx && null != valortrx.value()) ? valortrx.value().toString() : "")

        );
    }
}
