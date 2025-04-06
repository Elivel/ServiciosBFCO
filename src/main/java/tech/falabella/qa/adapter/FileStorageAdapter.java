package tech.falabella.qa.adapter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.CommandArgs;
import tech.falabella.qa.port.StoragePort;
import tech.falabella.qa.report.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(staticName = "newInstance")
public class FileStorageAdapter implements StoragePort {

    @Override
    public <T extends Tuple> void generate(Collection<T> result, CommandArgs args) {
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
                Estado de la Prueba:    %s
                """.formatted(
                System.getProperty("user.name"),
                args.getReport().name(),
                args.getParams().entrySet().stream()
                        .filter(it -> !it.getValue().isBlank())
                        .map(it -> it.getKey().concat(": ").concat(it.getValue()))
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now(),
                1,
                args.isExecuteExportReport()
                        ? "SQL Server Reporting Services: ".concat(args.getSsrsUrl())
                        : "Local file: ".concat(args.getCsvInput().getPath()),
                result.isEmpty() ? "Exitoso" : "Fallido"
        );

        String content = header.concat("\n")
                .concat(result.stream().map(it -> it.toString()).collect(Collectors.joining("\n")));

        try {
            Files.writeString(Path.of(args.getOutput().concat("out.txt")), content);
        } catch (IOException ignore) {
            log.error("Error generando archivo de salida {}", args.getOutput());
        }

        if (args.isPrint()) {
            log.info("\n{}\n", content);
        }

    }
}
