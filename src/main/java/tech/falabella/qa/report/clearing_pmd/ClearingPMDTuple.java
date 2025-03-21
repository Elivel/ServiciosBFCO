package tech.falabella.qa.report.clearing_pmd;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ClearingPMDTuple extends Tuple {

    private String ciclo;
    private String producto;
    private String definicionOrden;
    private String mti;
    private String functionCode;
    private String functionDescription;
    private String processingCode;
    private String conceptoClearingSettlement;

    @Override
    protected JsonObject getId() {
        return null;
    }

}
