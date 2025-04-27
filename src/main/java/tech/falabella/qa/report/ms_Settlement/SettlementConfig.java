package tech.falabella.qa.report.ms_Settlement;

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
public class SettlementConfig  implements ReportConfig<SettlementTuple> {

        private final Parameters parameters = Parameters.of(Map.of(
                "FechaProceso", Parameters.Value.builder()
                        .position(1)
                        .sqlFormat(dVal -> {
                            DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(dVal, formatterWeb);
                            return date.format(formatterSql);
                        }).build()
        ));
        private final String route = "Mastercard/Daily operation/SETTLEMENT (POSICIÓN NETA)";
        private final String query = """
                USE [CONCILIACION]
                DECLARE	@return_value int
                EXEC	@return_value = [nuevaconciliacion].[USP_MC_ObtenerDatosConciliadoCiclos]
                		@Fecha = ?
        """;
        private final int headerRowSize = 7;


        public SettlementTuple sqlMap (ResultSet resultSet) {
            try {
                return SettlementTuple.builder()
                        .compensacion(resultSet.getString(1))
                        .ciclo(resultSet.getString(4))
                        .monto_neto(resultSet.getString(5))
                        .monto_pesos(resultSet.getString(6))
                        .build();

            }catch (SQLException e){
                throw new MalformedTupleException(e);

            }
        }
        public SettlementTuple csvMap (String[] result){
            return SettlementTuple.builder()
                    .compensacion(result[0])
                    .ciclo(result[2])
                    .monto_neto(result[3])
                    .monto_pesos(result[4])
                    .build();
        }

}

