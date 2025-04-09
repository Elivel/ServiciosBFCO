package tech.falabella.qa.report.cl_ConsolidadoLiq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
@Getter
@NoArgsConstructor(staticName = "newInstance")
public class ConsolidadoLiqConfig implements ReportConfig<ConsolidadoLiqTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FECHALIQ", Parameters.Value.of(1)
    ));
    private final String route = "Conciliacion_Liquidacion/ConsolidadoLiq";
    private final String query = """
        SELECT DISTINCT FECHALIQ, CODEST, NOMEST, CODLOC, 
        NOMLOC, CANTTRX, IMPTRXMN, IMPIGVMN, IMPPRPMN, 
        IMPCOMNETMN, IMPCMSCMRMN, IMPRETFTEMN, IMPRETIVAMN, 
        IMPNETMN, IMPRETICAMN, IMPRETCREEMN, IMPINCMN,
        RETAVITAB, RETIMPBOM
        FROM TBL_CONSOLIDADOPORCONVENIOVTA
        WHERE (@FECHALIQ = ?) ORDER BY NOMEST
        """;
        
}
public void ConsolidadoLiqTuple sqlMap (ResultSet resultSet) {
    try {
        return ConsolidadoLiqTuple.builder()
                .nombrecomercio(resultSet.getString(1))
                .totalTrx(resultSet.getString(2))
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
                //segundatabla
                //liquidación de  Comercios REC
                .nombrecomercio(resultSet.getString(16))
                .totalTrx(resultSet.getString(17))
                .recaudoTotal(resultSet.getString(18))
                .comision(resultSet.getString(19))
                .ivaComision(resultSet.getString(20))
                .retencion(resultSet.getString(21))
                .netoADepositar(resultSet.getString(22))
                //tablatres
                //liquidación de  Comercios AVA
                .nombrecomercio(resultSet.getString(16))
                .totalTrx(resultSet.getString(17))
                .avanceTotal(resultSet.getString(18))
                .comision(resultSet.getString(19))
                .ivaComision(resultSet.getString(20))
                .retencion(resultSet.getString(21))
                .netoADepositar(resultSet.getString(22))
                .buil();
        tch (SQLException e){
            throw new MalformedTupleException(e);

        }
    }
    public final ConsolidadoLiqTuple csvMap (String[] result){

    }

}
