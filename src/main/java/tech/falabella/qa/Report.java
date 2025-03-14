package tech.falabella.qa;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum Report {
    CONSULTA_CLAI(Map.of("Tarjeta", 1, "NroAutorizacion", 2, "NroAutorOriginal", 3, "FechaTrx", 4)),
    DETALLADO_EVENTO_MMCA(Map.of()),
    CLEARING_PMD(Map.of());

    public final Map<String, Integer> parameters;

    public Map<Integer, String> validateAndGet(Map<String, String> dValues) {
        var result = new HashMap<Integer, String>();

        parameters.forEach((paramName, paramIndex) ->
                result.put(paramIndex, dValues.get(paramName)));

        return result;
    }

    public String getRealName() {
        return "";
    }

    public String getQuery() {
        return """
                USE [CONCILIACION]
                DECLARE @return_value int
                
                EXEC @return_value = [dbo].[SP_CONSULTA_CLAI]
                @Tarjeta = ?,
                @NroAutorizacion = ?,
                @NroAutorOriginal = ?,
                @FechaTrx = ?
                
                -- SELECT 'Return Value' = @return_value
                """;
    }

    public String getTmpFile() {
        return "/tmp"+this.name()+".csv";
    }
}
