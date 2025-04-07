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

    private String producto;
    private String mti;
    private String functionCode;
    private String processingCode;
    private String conceptoClearingSettlement;
    private String cantidadTrxCop;
    private String valoresTrxCop;
    private String tiiTrxCop;
    private String netoTrxCop;
    private String cantidadReversosTrxCop;
    private String valoresReversosTrxCop;
    private String tiiReversosTrxCop;
    private String netoReversosTrxCop;
    private String cantidadTrx;
    private String valoresCompresionTrxCop;
    private String tiiComprensionTrxCop;
    private String netoCompensacionTrxCop;

    @Override
    public JsonObject getId() {
        return null;
    }

}
