package tech.falabella.qa.report.cl_ConsolidadoLiq;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.DateTime;

import java.util.Map;
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ConsolidadoLiqTuple extends Tuple {
    private DateTime fechaLiq;
    private String codEst;
    private String nomEst;
    private String codLoc;
    private String nomLoc;
    private String cantTrx;
    private String impTrxMn;
    private String impIgvMn;
    private String impPrpMn;
    private String impComNetMn;
    private String impCmsCmrMn;
    private String impRetFteMn;
    private String impRetIvaMn;
    private String impNetMn;
    private String impRetIcaMn;
    private String impRetCreeMn;
    private String impIncMn;
    private String retAviTab;
    private String retImpBom;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("nombre-comercio", nomEst)

        );
    }
}
