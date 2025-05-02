package tech.falabella.qa.report.ms_Plantilla_Contable;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.DateTime;
import tech.falabella.qa.type.Money;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class PlantillaContableTuple extends Tuple {
    private DateTime compensacion;
    private String codigocuenta;
    private String nombretercero;
    private String nittercero;
    private String centrocosto;
    private Money debito;
    private Money credito;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Nombre Tercero a Afectar", nombretercero,
                        "NIT Tercero", nittercero)

        );
    }

}
