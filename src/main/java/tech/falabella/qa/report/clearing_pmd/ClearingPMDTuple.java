package tech.falabella.qa.report.clearing_pmd;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

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
    private Money valoresTrxCop;
    private Money tiiTrxCop;
    private Money netoTrxCop;
    private Money cantidadReversosTrxCop;
    private Money valoresReversosTrxCop;
    private Money tiiReversosTrxCop;
    private Money netoReversosTrxCop;
    private Number cantidadTrx;
    private Money valoresCompresionTrxCop;
    private Money tiiComprensionTrxCop;
    private Money netoCompensacionTrxCop;

    @Override
    public JsonObject getId() {
        return null;
    }

}
