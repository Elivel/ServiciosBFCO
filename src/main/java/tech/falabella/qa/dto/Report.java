package tech.falabella.qa.dto;

import lombok.RequiredArgsConstructor;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.report.clearing_pmd.ClearingPMDConfig;
import tech.falabella.qa.report.consulta_clai.ConsultaCLAIConfig;
import tech.falabella.qa.report.detallado_evento_mcca.DetalladoEventoMCCAConfig;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum Report {
    CONSULTA_CLAI(ConsultaCLAIConfig::newInstance),
    DETALLADO_EVENTO_MCCA(DetalladoEventoMCCAConfig::newInstance),
    CLEARING_PMD(ClearingPMDConfig::newInstance);

    public final Supplier<? extends ReportConfig> config;

}
