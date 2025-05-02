package tech.falabella.qa.dto;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.report.cl_ConsolidadoLiq.ConsolidadoLiqConfig;
import tech.falabella.qa.report.cl_Consulta_TRX_X_Documento.Consulta_TRX_X_DocumentoConfig;
import tech.falabella.qa.report.clearing_pmd.ClearingPMDConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;
import tech.falabella.qa.report.ms_Detallado_QMR.DetalladoQMRConfig;
import tech.falabella.qa.report.ms_Plantilla_Contable.PlantillaContableTuple;
import tech.falabella.qa.report.ms_Settlement.SettlementConfig;
import tech.falabella.qa.report.ms_Token_Metris.TokenMetrisConfig;
import tech.falabella.qa.report.msbil_ControlFacturamci.ControlFacturaMciConfig;
import tech.falabella.qa.report.ms_Plantilla_Contable.PlantillaContableConfig;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum Report {
    CONSULTA_CLAI(ConsultaCLAIConfig::newInstance, Boolean.TRUE),
    DETALLADO_EVENTO_MCCA(DetalladoEventoMCCAConfig::newInstance, Boolean.TRUE),
    CLEARING_PMD(ClearingPMDConfig::newInstance, Boolean.TRUE),
    CONSOLIDADO_LIQ(ConsolidadoLiqConfig::newInstance, Boolean.TRUE),
    CONSULTA_TRX_XDOCUMENTO(Consulta_TRX_X_DocumentoConfig::newInstance, Boolean.FALSE),
    CONTROL_FACTURA_MCI(ControlFacturaMciConfig::newInstance,Boolean.TRUE),
    SETTLEMENT(SettlementConfig:: newInstance,Boolean.TRUE),
    TOKEN_METRICS(TokenMetrisConfig::newInstance,Boolean.TRUE),
    DETALLADO_QMR(DetalladoQMRConfig::newInstance,Boolean.TRUE),
    PLANTILLA_CONTABLE_PMD(PlantillaContableConfig::newInstance,Boolean.TRUE);

    public final Supplier<ReportConfig<? extends Tuple>> config;
    public final boolean enabled;

}
