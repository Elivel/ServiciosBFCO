package tech.falabella.qa.report.consulta_clai;

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
public class ConsultaCLAIConfig implements ReportConfig<ConsultaCLAITuple> {

    private final Parameters parameters = Parameters.of(Map.of(
            "Tarjeta", Parameters.Value.of(1),
            "NroAutorizacion", Parameters.Value.of(2),
            "NroAutorOriginal", Parameters.Value.of(3),
            "FechaTrx", Parameters.Value.of(4)
    ));

    private final String route = "Conciliacion_Liquidacion/CONSULTA_CLAI";

    private final String query = """
            USE [CONCILIACION]
            DECLARE @return_value int
            
            EXEC @return_value = [dbo].[SP_CONSULTA_CLAI]
            @Tarjeta = ?,
            @NroAutorizacion = ?,
            @NroAutorOriginal = ?,
            @FechaTrx = ?
            """;

    public ConsultaCLAITuple sqlMap(ResultSet resultSet) {
        try {
            return ConsultaCLAITuple.builder()
                    .rta(resultSet.getString(1))
                    .numeroAutorizacion(resultSet.getString(2))
                    .tarjeta(resultSet.getString(3))
                    .cuenta(resultSet.getString(4))
                    .codigoTransaccionAdquiriente(resultSet.getString(5))
                    .valorOriginal(resultSet.getString(6))
                    .ultimoPaso(resultSet.getString(7))
                    .codigoRespuestaAdquiriente(resultSet.getString(8))
                    .fechaFinal(resultSet.getString(9))
                    .redAdquiriente(resultSet.getString(10))
                    .secuencia(resultSet.getString(11))
                    .autOriginal(resultSet.getString(12))
                    .fechaRegistroEncabezado(resultSet.getString(13))
                    .fechaRegistroDetalle(resultSet.getString(14))
                    .montoImpIVA(resultSet.getString(15))
                    .montoBaseIVA(resultSet.getString(16))
                    .propina(resultSet.getString(17))
                    .terminal(resultSet.getString(18))
                    .posEntryMode(resultSet.getString(19))
                    .nalInternal(resultSet.getString(20))
                    .numeroCuotas(resultSet.getString(21))
                    .comercio(resultSet.getString(22))
                    .build();
        } catch (SQLException e) {
            throw new MalformedTupleException(e);
        }
    }

    public final ConsultaCLAITuple csvMap(String[] result) {
        return ConsultaCLAITuple.builder()
                .rta(result[0])
                .numeroAutorizacion(result[1])
                .tarjeta(result[2])
                .cuenta(result[3])
                .codigoTransaccionAdquiriente(result[4])
                .valorOriginal(result[5])
                .ultimoPaso(result[6])
                .codigoRespuestaAdquiriente(result[7])
                .fechaFinal(result[8])
                .redAdquiriente(result[9])
                .secuencia(result[10])
                .autOriginal(result[11])
                .fechaRegistroEncabezado(result[12])
                .fechaRegistroDetalle(result[13])
                .montoImpIVA(result[14])
                .montoBaseIVA(result[15])
                .propina(result[16])
                .terminal(result[17])
                .posEntryMode(result[18])
                .nalInternal(result[19])
                .numeroCuotas(result[20])
                .comercio(result[21])
                .build();
    }

}
