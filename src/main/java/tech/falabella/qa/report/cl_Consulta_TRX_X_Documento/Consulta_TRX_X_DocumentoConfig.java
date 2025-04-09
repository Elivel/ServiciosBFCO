package tech.falabella.qa.report.cl_Consulta_TRX_X_Documento;
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
public class Consulta_TRX_X_DocumentoConfig implements ReportConfig<Consulta_TRX_X_DocumentoTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FINI", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    }).build(),
            "FFIN", Parameters.Value.builder()
                    .position(2)
                    .sqlFormat(dVal -> {
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    }).build(),
            "DOCUMENTO", Parameters.Value.of(3)
    ));
    private final String route = "Conciliacion_Liquidacion/Consulta_TRX_X_Documento";
    private final String query = """
            
            SELECT TOP 1000  CUENTAS.Documento ,   TBL.[JOU-FECHA] , 
            TBL.[JOU-FECHPROCESOCMR], TBL.[JOU-TARJETA] , 
            TBL.CUENTA ,[JOU-TRANSACCCREDITO] / 100
            FROM          TBL_JOURNAL_XCONCILIADO AS TBL WITH (NOLOCK)
            INNER JOIN   nuevaconciliacion.TBL_Cuentas AS CUENTAS WITH (NOLOCK)
            ON  TBL.[JOU-TARJETA] = CUENTAS.NumeroTarjeta
            WHERE     ([JOU-FECHPROCESOCMR] BETWEEN  '20230101' AND  '20231231')
            AND CUENTAS.Documento = '1012461912'
            """;

    //por definir la bd
    public Consulta_TRX_X_DocumentoTuple sqlMap(ResultSet resultSet) {
        try {
            return Consulta_TRX_X_DocumentoTuple.builder()
                    .canal(resultSet.getString(1))
                    .tipoTransaccion(resultSet.getString(2))
                    .tarjeta(resultSet.getString(3))
                    .numeroCuenta(resultSet.getString(4))
                    .codigoAutorizacion(resultSet.getString(5))
                    .fecha(resultSet.getString(6))
                    .hora(resultSet.getString(7))
                    .valorTransaccion(resultSet.getString(8))
                    .codigoConvenio(resultSet.getString(9))
                    .nombreComercio(resultSet.getString(10))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public Consulta_TRX_X_DocumentoTuple csvMap(String[] result) {
        return Consulta_TRX_X_DocumentoTuple.builder()
                .canal(result[0])
                .tipoTransaccion(result[1])
                .tarjeta(result[2])
                .numeroCuenta(result[3])
                .codigoAutorizacion(result[4])
                .fecha(result[5])
                .hora(result[6])
                .valorTransaccion(result[7])
                .codigoConvenio(result[8])
                .nombreComercio(result[9])
                .build();
    }
}




