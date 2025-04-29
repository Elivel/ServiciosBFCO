package tech.falabella.qa.report.cl_ConsolidadoLiq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.type.Money;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class ConsolidadoLiqConfig implements ReportConfig<ConsolidadoLiqTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FECHALIQ", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    }).build()
    ));
    private final String route = "Conciliacion_Liquidacion/ConsolidadoLiq";
    private final String query = """
            USE [CMRliquidaciones]
            DECLARE @fechaLiq DATE = ?;
            SELECT DISTINCT FECHALIQ, CODEST, NOMEST, CODLOC,
            NOMLOC, CANTTRX, IMPTRXMN, IMPIGVMN, IMPPRPMN,
            IMPCOMNETMN, IMPCMSCMRMN, IMPRETFTEMN, IMPRETIVAMN,
            IMPNETMN, IMPRETICAMN, IMPRETCREEMN, IMPINCMN,
            RETAVITAB, RETIMPBOM
            FROM TBL_CONSOLIDADOPORCONVENIOVTA
            WHERE FECHALIQ = @fechaLiq
            UNION
            SELECT DISTINCT FechaLiq, codest, nomest, codloc,
            nomloc, cantrx, imptrxmn, impcmsrecmn, impivacmsrecmn,
            impretcmsrecmn, impnetmn, null, null,
            null, null, null, null,
            null, null
            FROM TBL_CONSOLIDADOPORCONVENIOREC
            WHERE FechaLiq = @fechaLiq
            UNION
            SELECT FECHALIQ, CODEST, NOMEST, CODLOC,
            NOMLOC, CANTTRX, IMPTRXMN, IMPCMAVAMN, IMPIVACMSAVAMN,
            IMPRETCMSAVAMN, IMPNETMN, null, null,
            null, null, null, null,
            null, null
            FROM TBL_CONSOLIDADOPORCONVENIOAVA
            WHERE FECHALIQ = @fechaLiq
            ORDER BY NOMEST;
            """;

    private final int headerRowSize = 4;

    public ConsolidadoLiqTuple sqlMap(ResultSet resultSet) {
        try {
            return ConsolidadoLiqTuple.builder()
                    //.fechaLiq(DateTime.from(resultSet.getString(1)))
                    //.codEst(resultSet.getString(2))
                    .nomEst(resultSet.getString(3))
                   // .codLoc(resultSet.getString(4))
                    .nomLoc(resultSet.getString(5))
                    .cantTrx(resultSet.getString(6))
                    .impTrxMn(Money.from( resultSet.getString(7)))
                    .impIgvMn(Money.from(resultSet.getString(8)))
                    .impPrpMn(Money.from(resultSet.getString(9)))
                    .impComNetMn(Money.from(resultSet.getString(10)))
                    .impCmsCmrMn(Money.from(resultSet.getString(11)))
                    .impRetFteMn(Money.from(resultSet.getString(12)))
                    .impRetIvaMn(Money.from(resultSet.getString(13)))
                    .impNetMn(Money.from(resultSet.getString(14)))
                    .impRetIcaMn(Money.from(resultSet.getString(15)))
                    .impRetCreeMn(Money.from(resultSet.getString(16)))
                    .impIncMn(Money.from(resultSet.getString(17)))
                    .retAviTab(Money.from(resultSet.getString(18)))
                    .retImpBom(Money.from(resultSet.getString(19)))
                    .build();
        } catch (SQLException ignore) {
            return null;
        }
    }

    public ConsolidadoLiqTuple csvMap(String[] result) {
        try {
            return ConsolidadoLiqTuple.builder()
                    //.codEst(result[1])
                    .nomEst(result[0])
                  // .codLoc(result[2])
                    .nomLoc(result[15])
                    .cantTrx(result[16])
                    .impTrxMn(Money.from(result[17]))
                    .impIgvMn(Money.from(result[18]))
                    .impPrpMn(Money.from(result[19]))
                    .impComNetMn(Money.from(result[20]))
                    .impCmsCmrMn(Money.from(result[21]))
                    .impRetFteMn(Money.from(result[22]))
                    .impRetIvaMn(Money.from(result[23]))
                    .impNetMn(Money.from(result[24]))
                    .impRetIcaMn(Money.from(result[25]))
                    .impRetCreeMn(Money.from(result[26]))
                    .impIncMn(Money.from(result[27]))
                    .retAviTab(Money.from(result[28]))
                    .retImpBom(Money.from(result[29]))
                    .build();
        } catch (Exception ignore) {
            return null;
        }
    }
}