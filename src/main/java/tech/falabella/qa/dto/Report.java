package tech.falabella.qa.dto;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.report.cl_ConsolidadoLiq.ConsolidadoLiqConfig;
import tech.falabella.qa.report.cl_Consulta_TRX_X_Documento.Consulta_TRX_X_DocumentoConfig;
import tech.falabella.qa.report.clearing_pmd.ClearingPMDConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;
import tech.falabella.qa.report.msbil_ControlFacturamci.ControlFacturaMciConfig;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum Report {
    CONSULTA_CLAI(ConsultaCLAIConfig::newInstance, Boolean.TRUE),
    DETALLADO_EVENTO_MCCA(DetalladoEventoMCCAConfig::newInstance, Boolean.TRUE),
    CLEARING_PMD(ClearingPMDConfig::newInstance, Boolean.FALSE),
    CONSOLIDADO_LIQ(ConsolidadoLiqConfig::newInstance, Boolean.TRUE),
    CONSULTA_TRX_XDOCUMENTO(Consulta_TRX_X_DocumentoConfig::newInstance, Boolean.FALSE),
    CONTROL_FACTURA_MCI(ControlFacturaMciConfig::newInstance,Boolean.TRUE);

    public final Supplier<ReportConfig<? extends Tuple>> config;
    public final boolean enabled;

}
