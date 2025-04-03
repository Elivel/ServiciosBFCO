package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import picocli.CommandLine;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.service.ValidationServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class Application {

    public static void main(String[] args) {
        var commandLine = new CommandLine(CommandArgs.newInstance());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        commandLine.parseArgs(args);

        int exitCode = commandLine.execute(args);
        var command = commandLine.<CommandArgs>getCommand();
        try {
            var reportValidation = ValidationServiceImpl.newInstance(command.generateInput(),
                    command.generatePersistence());

            final var result = reportValidation.processElements();

            var outPrint = printResultInConsole(result, command);
            if (command.isPrint()) {
                log.info(outPrint);
            }

            Files.writeString(Path.of("out.txt"), outPrint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.exit(exitCode);
    }

    @SneakyThrows
    public static void scraping() {
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(
                    headline.attr("title") + "\n\t" + headline.absUrl("href"));
        }
    }

    private static <T extends Tuple> String printResultInConsole(Collection<T> result, CommandArgs args) {
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
                args.report.name(),
                args.params.entrySet().stream()
                        .filter(it -> !it.getValue().isBlank())
                        .map(it -> it.getKey().concat(": ").concat(it.getValue()))
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now(),
                1,
                args.executeExportReport
                        ? "SQL Server Reporting Services: ".concat(args.ssrsUrl)
                        : "Local file: ".concat(args.csvInput.getPath()),
                result.isEmpty() ? "Exitoso" : "Fallido"
        );

        return "\n"
                .concat(header)
                .concat("\n")
                .concat(result.stream().map(it -> it.toString()).collect(Collectors.joining("\n")));
    }

}