package tech.falabella.qa.report.cl_ConsolidadoLiq;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;

import java.util.Map;
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ConsolidadoLiqTuple extends Tuple {
    private String nombreComercioVTA;
    private String totalTrxVTA;
    private String ventaTotal;
    private String propina;
    private String iva;
    private String ventaNeta;
    private String comision;
    private String retencion;
    private String reteiva;
    private String netoadepositar;
    private String reteica;
    private String reteCree;
    private String impConsumo;
    private String retAviyTabler;
    private String retSobBombe;
    //segundatabla liquidación de  Comercios REC
    private String nombreComercioREC;
    private String totalTrxREC;
    private String recaudoTotalREC;
    private String comisionREC;
    private String ivaComisionREC;
    private String retencionREC;
    private String netoADepositarREC;
    //tablatres liquidación deComercios AVA
    private String nombreComercioAVA;
    private String totalTrxAVA;
    private String avanceTotalAVA;
    private String comisionAVA;
    private String ivaComisionAVA;
    private String retencionAVA;
    private String netoADepositarAVA;

    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Nombre Comercio", nombreComercioVTA)

        );
    }
}
