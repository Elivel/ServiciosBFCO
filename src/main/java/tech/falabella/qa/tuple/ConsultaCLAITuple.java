package tech.falabella.qa.tuple;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class ConsultaCLAITuple extends Tuple {

    private String validacion;
    private String numeroAutorizacion;
    private String tarjeta;
    private String cuenta;
    private String codigoTransaccionAdquiriente;
    private String valorOriginal;
    private String ultimoPaso;
    private String codigoRespuestaAdquiriente;
    private String fechaFinal;
    private String redAdquiriente;
    private String secuencia;
    private String autOriginal;
    private String fechaRegistroEncabezado;
    private String fechaRegistroDetalle;
    private String montoImpIVA;
    private String montoBaseIVA;
    private String propina;
    private String terminal;
    private String marcaPignoracion;
    private String marcaInternacional;
    private String numeroCuotas;
    private String comercio;

    public static final Function<ResultSet, ConsultaCLAITuple> sqlMap = (resultSet) -> {
        try {
            return ConsultaCLAITuple.builder()
                    .validacion(resultSet.getString(1))
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
                    .marcaPignoracion(resultSet.getString(19))
                    .marcaInternacional(resultSet.getString(20))
                    .numeroCuotas(resultSet.getString(21))
                    .comercio(resultSet.getString(22))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public static final Function<String[], ConsultaCLAITuple> csvMap = (result) ->
            ConsultaCLAITuple.builder()
                    .validacion(result[0])
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
                    .marcaPignoracion(result[18])
                    .marcaInternacional(result[19])
                    .numeroCuotas(result[20])
                    .comercio(result[21])
                    .build();

}
