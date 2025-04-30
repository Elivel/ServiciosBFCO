package tech.falabella.qa.report.ms_Settlement;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.Money;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class SettlementTuple extends Tuple {
    private String compensacion;
    private String ciclo;
    private Money monto_neto;
    private Money monto_pesos;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Compensación", compensacion,
                        "Ciclo",ciclo)

        );
    }

}
