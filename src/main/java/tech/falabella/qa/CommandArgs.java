package tech.falabella.qa;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Command(name = "ssrs-validator", mixinStandardHelpOptions = true, version = {
        "ssrs-validator 0.1.0-SNAPSHOT",
        "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
        "OS: ${os.name} ${os.version} ${os.arch}"},
        description = "valida la generación de reportes en SSRS")
public class CommandArgs {

    @Option(names = "-D", mapFallbackValue = Option.NULL_VALUE)
    Map<String, Optional<String>> params;

    @Option(names = {"-f", "--file"}, paramLabel = "ARCHIVE", description = "the archive file")
    File archive;

    // Database connection
    @Option(names = {"-u", "--url"}, description = "URL to access database")
    String mssqlUrl;

    @Option(names = {"-U", "--username"}, description = "User name", defaultValue = "sa", fallbackValue = "sa")
    String username;

    @Option(names = {"-P", "--password"}, description = "Passphrase", interactive = true)
    char[] password;

    // others
    @Option(names = {"-p", "--print"}, description = "display output", negatable = true, defaultValue = "false", fallbackValue = "false" )
    boolean print;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean helpRequested = false;

    @Option(names = {"-V", "--version"}, versionHelp = true,
            description = "print version information")
    boolean versionRequested;

}
