package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.tuple.ConsultaCLAITuple;

import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
@NoArgsConstructor
public class Application extends CommandArgs implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        final var input = new FileReportAdapter<>(this.archive, ",", ConsultaCLAITuple.csvMap);
        final var persistence = new DDBBReportAdapter(DDBBReportAdapter.createConnection(this.mssqlUrl, this.username, this.password),
                """
                        USE [CONCILIACION]
                        DECLARE @return_value int
                        
                        EXEC @return_value = [dbo].[SP_CONSULTA_CLAI]
                        @Tarjeta = ?,
                        @NroAutorizacion = ?,
                        @NroAutorOriginal = ?,
                        @FechaTrx = ?
                        
                        -- SELECT 'Return Value' = @return_value
                        """, Map.of(1, "", 2, "399300", 3, "", 4, ""),
                ConsultaCLAITuple.sqlMap);

        var reportValidation = ValidationServiceImpl.newInstance();
        reportValidation.setInputData(input);
        reportValidation.setPersistenceData(persistence);

        final var result = reportValidation.processElements();

        if (print) {
            result.forEach(e -> {
                System.out.println(e);
            });
        }

        return 0;
    }

}