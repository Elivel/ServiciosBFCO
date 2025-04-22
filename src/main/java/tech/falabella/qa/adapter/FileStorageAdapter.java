package tech.falabella.qa.adapter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.CommandArgs;
import tech.falabella.qa.dto.Result;
import tech.falabella.qa.port.StoragePort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(staticName = "newInstance")
public class FileStorageAdapter implements StoragePort {

    @Override
    public void generate(Result result, CommandArgs args) {
        var header = """
                Área:                   BFCO Cluster Proyectos Corporativos - Quality Assurance
                Nombre del Analista:    %s
                Caso de Prueba:         %s
                Certificación:          Migración reportes de Conciliación
                Datos De Prueba:        %s
                Fecha de Ejecución:     %tF %<tR
                Ciclo de Pruebas:       %d
                Plataforma:
                    %s
                Precondición:           Tablas pobladas
                Resultado de la Prueba: %s
                    Cantidad de datos en reporte:       %d
                    Cantidad de datos en base de datos: %d
                    Cantidad de datos inconsistentes:   %d
                
                """.formatted(
                System.getProperty("user.name"),
                args.getReport().name(),
                (null == args.getParams())
                        ? ""
                        : args.getParams().entrySet().stream()
                        .filter(it -> !it.getValue().isBlank())
                        .map(it -> it.getKey().concat(": ").concat(it.getValue()))
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now(),
                1,
                args.isExecuteExportReport()
                        ? "SQL Server Reporting Services: ".concat(args.getSsrsUrl())
                        : "Local file: ".concat(args.getCsvInput().getPath()),
                result.status(),
                result.inputCount(),
                result.persistenceCount(),
                result.inconsistenciesCount()
        );

        String content = header.concat(String.join("\n", result.inconsistencies()));

        if (args.isPrint()) {
            log.info("\n{}\n", content);
        }

        try {
            Files.writeString(Path.of(args.getOutput()), content);
        } catch (IOException ignore) {
            log.error("Error generando archivo de salida {}", args.getOutput());
        }

    }
}
