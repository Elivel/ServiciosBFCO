package tech.falabella.qa.report.msbil_ControlFacturamci;
import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.util.Date;
import java.util.Map;
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ControlFacturaMciTuple extends Tuple {
private DateTime fecha;
private String eventCode;
private String groupServiceCode;
private String name;
private Money valueA;
private Money rate;
private Money charge;
private Money valorInvoice;
private Money rateValor;
private Money chargeInvoide;
private String efectividadControlFacturaMci;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Grupo Servicio", groupServiceCode,
                        "Nombre", name)

        );
    }
}
