package tech.falabella.qa.report.ms_Settlement;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class SettlementTuple extends Tuple {
    private String compensacion;
    private String ciclo;
    private String monto_neto;
    private String monto_pesos;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Compensación", compensacion,
                        "Ciclo",ciclo)

        );
    }

}
