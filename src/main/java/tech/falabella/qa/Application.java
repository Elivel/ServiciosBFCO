package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

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
        final ValidationService reportValidation = ValidationServiceImpl.newInstance();

        final var input = reportValidation.getInputData();
        final var persistence = reportValidation.getPersistenceData();

        final var result = reportValidation.processElements(input, persistence);

        return 0;
    }

}