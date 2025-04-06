package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.adapter.FileStorageAdapter;
import tech.falabella.qa.service.ValidationServiceImpl;

@Slf4j
@NoArgsConstructor
public class Application {

    public static void main(String[] args) {
        var commandLine = new CommandLine(CommandArgs.newInstance());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        commandLine.parseArgs(args);

        int exitCode = commandLine.execute(args);

        var command = commandLine.<CommandArgs>getCommand();

        var reportValidation = ValidationServiceImpl.newInstance(command.generateInput(),
                command.generatePersistence());

        final var result = reportValidation.processElements();

        var fileOutput = FileStorageAdapter.newInstance();
        fileOutput.generate(result, command);

        System.exit(exitCode);
    }

}