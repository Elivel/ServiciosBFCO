package tech.falabella.qa.report.consulta_clai;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ConsultaCLAITuple extends Tuple {

    private String rta;
    private String numeroAutorizacion;
    private String tarjeta;
    private String cuenta;
    private String codigoTransaccionAdquiriente;
    private String valorOriginal;
    private String ultimoPaso;
    private String codigoRespuestaAdquiriente;
    private String fechaFinal;
    private String redAdquiriente;
    private String secuencia;
    private String autOriginal;
    private String fechaRegistroEncabezado;
    private String fechaRegistroDetalle;
    private String montoImpIVA;
    private String montoBaseIVA;
    private String propina;
    private String terminal;
    private String posEntryMode;
    private String nalInternal;
    private String numeroCuotas;
    private String comercio;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("numero-autorizacion", numeroAutorizacion,
                        "tarjeta", tarjeta,
                        "cuenta", cuenta)
        );
    }

}
