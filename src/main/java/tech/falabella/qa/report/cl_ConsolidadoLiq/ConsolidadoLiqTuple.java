package tech.falabella.qa.report.cl_ConsolidadoLiq;

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
public class ConsolidadoLiqTuple extends Tuple {
    //private DateTime fechaLiq;
    //private String codEst;
    private String nomEst;
    //private String codLoc;
    private String nomLoc;
    private String cantTrx;
    private Money impTrxMn;
    private Money impIgvMn;
    private Money impPrpMn;
    private Money impComNetMn;
    private Money impCmsCmrMn;
    private Money impRetFteMn;
    private Money impRetIvaMn;
    private Money impNetMn;
    private Money impRetIcaMn;
    private Money impRetCreeMn;
    private Money impIncMn;
    private Money retAviTab;
    private Money retImpBom;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("nombre-establecimiento", nomEst,
                        "nombre-local",nomLoc)

        );
    }
}
