package tech.falabella.qa.report.cl_ConsolidadoLiq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
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
        SELECT DISTINCT FECHALIQ, CODEST, NOMEST, CODLOC, 
        NOMLOC, CANTTRX, IMPTRXMN, IMPIGVMN, IMPPRPMN, 
        IMPCOMNETMN, IMPCMSCMRMN, IMPRETFTEMN, IMPRETIVAMN, 
        IMPNETMN, IMPRETICAMN, IMPRETCREEMN, IMPINCMN,
        RETAVITAB, RETIMPBOM
        FROM TBL_CONSOLIDADOPORCONVENIOVTA
        WHERE (FECHALIQ = ?) ORDER BY NOMEST
        """;


    public ConsolidadoLiqTuple sqlMap (ResultSet resultSet) {
        try {
            return ConsolidadoLiqTuple.builder()
                    .nombreComercioVTA(resultSet.getString(1))
                    .totalTrxVTA(resultSet.getString(2))
                    .ventaTotal(resultSet.getString(3))
                    .iva(resultSet.getString(4))
                    .propina(resultSet.getString(5))
                    .ventaNeta(resultSet.getString(6))
                    .comision(resultSet.getString(7))
                    .retencion(resultSet.getString(8))
                    .reteiva(resultSet.getString(9))
                    .netoadepositar(resultSet.getString(10))
                    .reteica(resultSet.getString((11)))
                    .reteCree(resultSet.getString(12))
                    .impConsumo(resultSet.getString(13))
                    .retAviyTabler(resultSet.getString(14))
                    .retSobBombe(resultSet.getString(15))
                    //segundatabla liquidación de  Comercios REC
                    .nombreComercioREC(resultSet.getString(16))
                    .totalTrxREC(resultSet.getString(17))
                    .recaudoTotalREC(resultSet.getString(18))
                    .comision(resultSet.getString(19))
                    .ivaComisionREC(resultSet.getString(20))
                    .retencionREC(resultSet.getString(21))
                    .netoADepositarREC(resultSet.getString(22))
                    //tablatres
                    //liquidación de  Comercios AVA
                    .nombreComercioAVA(resultSet.getString(16))
                    .totalTrxAVA(resultSet.getString(17))
                    .avanceTotalAVA(resultSet.getString(18))
                    .comisionAVA(resultSet.getString(19))
                    .ivaComisionAVA(resultSet.getString(20))
                    .retencion(resultSet.getString(21))
                    .retencionAVA(resultSet.getString(22))
                    .build();
        }
        catch (SQLException e){
                throw new MalformedTupleException(e);

            }
        }
        public ConsolidadoLiqTuple csvMap (String[] result){
        return ConsolidadoLiqTuple.builder()
                .nombreComercioVTA(result[0])
                .totalTrxVTA(result[1])
                .ventaTotal(result[2])
                .iva(result[3])
                .propina(result[4])
                .ventaNeta(result[5])
                .comision(result[6])
                .retencion(result[7])
                .reteiva(result[8])
                .netoadepositar(result[9])
                .reteica(result[10])
                .reteCree(result[11])
                .impConsumo(result[12])
                .retAviyTabler(result[13])
                .retSobBombe(result[14])
                //liquidación de  Comercios REC
                .nombreComercioREC(result[15])
                .totalTrxREC(result[16])
                .recaudoTotalREC(result[17])
                .comisionREC(result[18])
                .ivaComisionREC(result[19])
                .retencionREC(result[20])
                .netoADepositarREC(result[21])
                //liquidación deComercios AVA
                .nombreComercioAVA(result[22])
                .totalTrxAVA(result[23])
                .avanceTotalAVA(result[24])
                .comisionAVA(result[25])
                .ivaComisionAVA(result[26])
                .retencionAVA(result[27])
                .netoADepositarAVA(result[28])
                .build();
        }
}