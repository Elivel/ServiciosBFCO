package tech.falabella.qa.report.msbil_ControlFacturamci;
import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ControlFacturaMciTuple extends Tuple {
private String fecha;
private String eventCode;
private String groupServiceCode;
private String name;
private String valueA;
private String rate;
private String charge;
private String valorInvoice;
private String rateValor;
private String chargeInvoide;
private String efectividadControlFacturaMci;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Grupo Servicio", groupServiceCode,
                        "Nombre", name)

        );
    }
}
